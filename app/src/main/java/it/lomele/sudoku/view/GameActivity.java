package it.lomele.sudoku.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;

import it.lomele.sudoku.database.ScoreDatabase;
import it.lomele.sudoku.database.ScoreDbController;
import it.lomele.sudoku.R;
import it.lomele.sudoku.control.GameService;
import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.utils.GridManager;
import it.lomele.sudoku.view.fragments.EndGameFragment;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGridActivity";

    private List<Cell> plainGrid;
    private List<Cell> solvedGrid;
    private int level;

    private SudokuBoardAdapter mAdapter;
    private Holder holder;

    private Chronometer simpleChronometer;
    private long elapsedTime;

    private ScoreDatabase db;

    private GameService mService;

    private int usedHints = 0;
    private int maxHints;
    private int usedAttempts = 1;
    private int maxAttempts;


    // Handler for handling messages sent by SolveThread and HintThread
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
        setContentView(R.layout.activity_game);

        simpleChronometer = findViewById(R.id.simpleChronometer);
        startChronometer();

        // Get the Database instance
        db = Room.databaseBuilder(getApplicationContext(), ScoreDatabase.class, "ScoreDatabase").build();

        // Instance a GameService object that controls the Threads
        mService = new GameService(handler);

        Intent data = getIntent();
        setLevel(data.getIntExtra("difficulty", 1));
        holder = new Holder();

        setGrid();


    }

    /*
    Set level of difficulty and correct amount of maxAttempts and maxHints
     */
    public void setLevel(int difficulty){
        switch(difficulty){
            case(Constant.DIFFICULTY_EASY):
                this.level = difficulty;
                maxAttempts = 5;
                break;
            case(Constant.DIFFICULTY_MEDIUM):
                this.level = difficulty;
                maxHints = 9;
                maxAttempts = 3;
                break;
            case(Constant.DIFFICULTY_HARD):
                this.level = difficulty;
                maxHints = 6;
                maxAttempts = 1;
                break;
        }
    }

    /*
    Starts the chronometer
     */
    public void startChronometer(){
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start();
    }

    /*
    Stops the chronometer and returnss its value
     */
    public long stopChronometer(){
        simpleChronometer.stop();
        elapsedTime = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
        return elapsedTime;
    }

    /*
    Generates the grid and its solution and draws the grid
     */
    public void setGrid(){
        // GENERATING GRID
        GameMatrix gameMatrix = Creator.createFull();
        Riddle riddle = Creator.createRiddle(gameMatrix);
        plainGrid = GridManager.fromGameMatrixToCellArray(riddle);

        // GENERATING SOLUTION WITH A THREAD
        mService.solve(riddle);

        // GENERATING AND SETTING GRIDVIEW
        GridView gridView = (GridView) findViewById(R.id.gvGrid);

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

        /*
        Set a new Value in selected cell
         */
        public void setVal(int position, int newVal){
            Cell cell = plainGrid.get(position);
            if(cell.isEditable()){
                cell.setValue(newVal);
            }else{  // Warn the user that selected cell is not editable
                Toast.makeText(GameActivity.this, getResources().getString(R.string.toast_cell_not_editable), Toast.LENGTH_SHORT).show();
            }
        }


        /*
        It checks if there are any available hints
         */
        public boolean checkForAvailableHints(){
            if(level == Constant.DIFFICULTY_EASY)   // Infinite hints in Easy mode
                return true;
            if(usedHints<maxHints){
                usedHints++;
                if(usedHints == maxHints) {     // Disable the hint button because this was the last hint
                    btnHint.setEnabled(false);
                    btnHint.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_disabled_dark)));
                }
                return true;
            }
            return false;
        }

        /*
        It checks if there are any available attempts
         */
        public boolean checkForAvailableAttempts(){
            if(usedAttempts<maxAttempts) {
                usedAttempts++;
                if (usedAttempts == maxAttempts)    // Warn the user that this is his last attempt
                    Toast.makeText(GameActivity.this, getString(R.string.toast_last_attempt), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        /*
        Set the TextView with the number of used hints
         */
        public void setHints(){
            if(level == Constant.DIFFICULTY_EASY)
                tvHints.setText("∞");
            else
                tvHints.setText(usedHints+"/"+maxHints);
        }

        /*
        Set the TextView with the number of used attempts
         */
        public void setAttempts(){
            tvAttempts.setText(usedAttempts+"/"+maxAttempts);
        }

        /*
        Prepares the end game fragment and stores the game result in the DB
         */
        public void endGame(boolean result) {
            EndGameFragment fragment;
            LinearLayout layout = findViewById(R.id.layout);

            String time = ScoreDbController.convertLongToParsedString(stopChronometer());

            // Put game details in a bundle
            Bundle bundle = new Bundle();
            bundle.putInt("level", level);
            bundle.putString("time", time);
            bundle.putString("attempts", tvAttempts.getText().toString());
            bundle.putString("hints", tvHints.getText().toString());

            // Insert a new score in DB and prepare de fragment
            if (result) {
                controller.insertNewScore(time, level, plainGrid, 1);
                Toast.makeText(GameActivity.this, getString(R.string.toast_win), Toast.LENGTH_SHORT).show();
                fragment = new EndGameFragment(1, bundle);
            } else {
                controller.insertNewScore(time, level, plainGrid, 0);
                btnSolve.setEnabled(false);
                btnSolve.setActivated(false);
                Toast.makeText(GameActivity.this, getString(R.string.toast_lose), Toast.LENGTH_SHORT).show();
                fragment = new EndGameFragment(0, bundle);
            }

            // Show the end game fragment
            layout.setVisibility(View.GONE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, fragment)
                    .commit();

        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.btn1):
                    setVal(currentCell, 1);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn2):
                    setVal(currentCell, 2);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn3):
                    setVal(currentCell, 3);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn4):
                    setVal(currentCell, 4);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn5):
                    setVal(currentCell, 5);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn6):
                    setVal(currentCell, 6);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn7):
                    setVal(currentCell, 7);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn8):
                    setVal(currentCell, 8);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btn9):
                    setVal(currentCell, 9);
                    mAdapter.notifyDataSetChanged();
                    break;
                case(R.id.btnDel):
                    setVal(currentCell, 0);
                    mAdapter.notifyDataSetChanged();
                    break;

		        case(R.id.btnHint):
		            if(checkForAvailableHints()) {
                        mService.hint(plainGrid);
                        setHints();
                    }else{
                        Toast.makeText(GameActivity.this, getString(R.string.toast_no_hints), Toast.LENGTH_SHORT).show();
                    }
                    break;
                
                case(R.id.btnSolve):
                    if(GameService.check(plainGrid, solvedGrid)){
                        endGame(true);
                    }else {
                        if(checkForAvailableAttempts()) {
                            Toast.makeText(GameActivity.this, getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                            setAttempts();
                        }else {
                            endGame(false);
                        }
                    }
                    break;
            }
        }

        /*
        Set the position of the selected cell
        and notify the Adapter that it has to highlight the cell and its row, col and block
         */
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            this.currentCell = position;
            Cell cell = plainGrid.get(position);
            plainGrid = GridManager.highlight(plainGrid, cell.getRow(), cell.getCol(), cell.getBlock());
            mAdapter.notifyDataSetChanged();
        }
    }

}