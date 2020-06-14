package it.lomele.sudoku.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.text.ParseException;
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
    private int difficulty;
    private Holder holder;
    protected List<Cell> plainGrid;
    protected List<Cell> solvedGrid;
    protected List<List<Cell>> matrixGrid;
    protected SudokuBoardAdapter mAdapter;
    private Chronometer simpleChronometer;
    protected Db db;
    protected GameService mService;

    private ProgressBar progBar;
    private LinearLayout layout;

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
        simpleChronometer.start();
        db = Room.databaseBuilder(getApplicationContext(), Db.class, "ScoreDatabase").build();

        holder = new Holder();
        mService = new GameService(handler);

        progBar = findViewById(R.id.indeterminateBar);
        layout = findViewById(R.id.layout);

        progBar.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);

        Intent data = getIntent();
        difficulty = data.getIntExtra("difficulty", 1);
        setGrid();
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

        //matrixGrid = GridManager.fromCellArrayToCellMatrix(grid);
        progBar.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

    }


    private class Holder implements View.OnClickListener, AdapterView.OnItemClickListener{
        private Button btnSolve;
        private Button btnHint;
        private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
        private Button btnDel;
        private int currentCell;
        private ScoreDbController controller = new ScoreDbController(getApplicationContext());

        public Holder(){
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
                    mService.hint(plainGrid);
                    break;
                
                case(R.id.btnSolve):/*
                    plainGrid.clear();
                    plainGrid.addAll(solvedGrid);
                    mAdapter.notifyDataSetChanged();*/
                    if(GameService.check(plainGrid, solvedGrid)){
			            simpleChronometer.stop();
                        try {
                            controller.insertNewScore(simpleChronometer.getContentDescription().toString(), "easy");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(GameActivity.this, "You won!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(GameActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
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
}