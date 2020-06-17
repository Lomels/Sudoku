package it.lomele.sudoku.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;
import java.util.List;
import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import it.lomele.sudoku.DATABASE.Db;
import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;
import it.lomele.sudoku.control.GameService;
import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.utils.GridManager;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGridActivity";

    private List<Cell> plainGrid;
    private List<Cell> solvedGrid;
    private String level;

    private SudokuBoardAdapter mAdapter;
    private Holder holder;

    private Chronometer simpleChronometer;
    private long elapsedTime;

    private Db db;

    private GameService mService;

    private int usedHints = 0;
    private int maxHints;
    private int usedAttempts = 0;
    private int maxAttempts;


    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            Bundle bundle = inputMessage.getData();
            switch(inputMessage.what){
                case(Constant.SOLUTION_MSG):
                    solvedGrid = (List<Cell>) bundle.getSerializable("solved");
                    break;
                case(Constant.HINT_MSG):
                    plainGrid = (List<Cell>) bundle.getSerializable("board");
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        simpleChronometer = findViewById(R.id.simpleChronometer);
        startChronometer();

        db = Room.databaseBuilder(getApplicationContext(), Db.class, "ScoreDatabase").build();

        mService = new GameService(handler);

        Intent data = getIntent();
        setLevel(data.getIntExtra("difficulty", 1));

        setGrid();
        holder = new Holder();

    }

    public void setLevel(int level){
        switch(level){
            case(Constant.DIFFICULTY_EASY):
                this.level = getString(R.string.button_easy);
                maxAttempts = 5;
                break;
            case(Constant.DIFFICULTY_MEDIUM):
                this.level = getString(R.string.button_medium);
                maxHints = 6;
                maxAttempts = 3;
                break;
            case(Constant.DIFFICULTY_HARD):
                this.level = getString(R.string.button_hard);
                maxHints = 9;
                maxAttempts = 1;
                break;
        }
    }

    public void startChronometer(){
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start();
    }

    public long stopChronometer(){
        simpleChronometer.stop();
        elapsedTime = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
        return elapsedTime;
    }


    public void setGrid(){
        // GENERATING GRID
        GameMatrix gameMatrix = Creator.createFull();
        Riddle riddle = Creator.createRiddle(gameMatrix);
        plainGrid = GridManager.fromGameMatrixToCellArray(riddle);

        // GENERATING SOLUTION WITH A THREAD
        mService.solve(riddle);

        // GENERATING GRIDVIEW
        GridView gridView = (GridView) findViewById(R.id.gvGrid);

        Log.d(TAG, "Size "+plainGrid.size());
        mAdapter = new SudokuBoardAdapter(getApplicationContext(), plainGrid);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(holder);

    }

    private class Holder implements View.OnClickListener, AdapterView.OnItemClickListener{
        private Button btnSolve;
        private Button btnHint;
        private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
        private Button btnDel;

        private TextView tvHints;
        private TextView tvAttempts;

        private int currentCell;
        private ScoreDbController controller = new ScoreDbController(getApplicationContext());

        public Holder(){
            tvAttempts = findViewById(R.id.tvAttempts);
            tvHints = findViewById(R.id.tvHints);

            btnSolve = findViewById(R.id.btnSolve);
            btnDel = findViewById(R.id.btnDel);
            btnHint = findViewById(R.id.btnHint);
            btn1 = findViewById(R.id.btn1);
            btn2 = findViewById(R.id.btn2);
            btn3 = findViewById(R.id.btn3);
            btn4 = findViewById(R.id.btn4);
            btn5 = findViewById(R.id.btn5);
            btn6 = findViewById(R.id.btn6);
            btn7 = findViewById(R.id.btn7);
            btn8 = findViewById(R.id.btn8);
            btn9 = findViewById(R.id.btn9);

            btnSolve.setOnClickListener(this);
            btnDel.setOnClickListener(this);
            btnHint.setOnClickListener(this);
            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);
            btn4.setOnClickListener(this);
            btn5.setOnClickListener(this);
            btn6.setOnClickListener(this);
            btn7.setOnClickListener(this);
            btn8.setOnClickListener(this);
            btn9.setOnClickListener(this);

            setHints();
            setAttempts();
        }

        public void set(int position, int newVal){
            Cell cell = plainGrid.get(position);
            if(cell.isEditable()){
                cell.setValue(newVal);
                return;
            }else{
                Toast.makeText(GameActivity.this, getResources().getString(R.string.msg_cell_not_editable), Toast.LENGTH_SHORT).show();
            }
        }

        public boolean checkForAvailableHints(){
            if(usedHints<maxHints){
                usedHints++;
                if(usedHints == maxHints)
                    btnHint.setEnabled(false);
                return true;
            }
            return false;
        }

        public boolean checkForAvailableAttempts(){
            if(usedAttempts<maxAttempts) {
                usedAttempts++;
                if (usedAttempts == maxAttempts)
                    Toast.makeText(GameActivity.this, getString(R.string.toast_last_attempt), Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        }

        public void setHints(){
            tvHints.setText(usedHints+"/"+maxHints);
        }

        public void setAttempts(){
            tvAttempts.setText(usedAttempts+"/"+maxAttempts);
        }

        public void endGame(){

            btnSolve.setEnabled(false);
            DialogFragment dialog = new Dialog();
            dialog.show(getSupportFragmentManager(), "missiles");
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.btn1):
                    set(currentCell, 1);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn2):
                    set(currentCell, 2);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn3):
                    set(currentCell, 3);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn4):
                    set(currentCell, 4);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn5):
                    set(currentCell, 5);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn6):
                    set(currentCell, 6);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn7):
                    set(currentCell, 7);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn8):
                    set(currentCell, 8);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn9):
                    set(currentCell, 9);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btnDel):
                    set(currentCell, 0);
                    mAdapter.notifyDataSetChanged();
                    break;

		        case(R.id.btnHint):
		            if(checkForAvailableHints()) {
                        mService.hint(plainGrid);
                        usedHints++;
                        setHints();
                    }else{
                        Toast.makeText(GameActivity.this, getString(R.string.toast_no_hints), Toast.LENGTH_SHORT).show();
                    }
                    break;
                
                case(R.id.btnSolve):
                    if(GameService.check(plainGrid, solvedGrid)){
                        controller.insertNewScore(ScoreDbController.convertLongToParsedString(stopChronometer()), level);
                        Log.d(TAG, "New score inserted: "+ScoreDbController.convertLongToParsedString(stopChronometer()));
                        Toast.makeText(GameActivity.this, getString(R.string.toast_win), Toast.LENGTH_SHORT).show();
                    }else {
                        if(checkForAvailableAttempts()) {
                            Toast.makeText(GameActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
                            setAttempts();
                        }else {
                            Toast.makeText(GameActivity.this, getString(R.string.toast_lose), Toast.LENGTH_SHORT).show();
                            endGame();
                        }
                    }
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            this.currentCell = position;
            Cell cell = plainGrid.get(position);
            plainGrid = GridManager.highlight(plainGrid, cell.getRow(), cell.getCol(), cell.getBlock());
            mAdapter.notifyDataSetChanged();
        }
    }

    public static class Dialog extends DialogFragment{
        @Override
        public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                    // Add action buttons
                    .setPositiveButton("signin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Dialog.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }


    }
}