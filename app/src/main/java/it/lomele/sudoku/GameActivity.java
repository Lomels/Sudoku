package it.lomele.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGridActivity";
    private JSONObject jsonObject;
    private Holder holder;
    protected List<Cell> plainGrid;
    protected SudokuBoardAdapter mAdapter;
    private Chronometer simpleChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        simpleChronometer = findViewById(R.id.simpleChronometer);
        simpleChronometer.start();

        holder = new Holder();

        Intent data = getIntent();
        Bundle bundle = data.getBundleExtra("bundle");
        List<List<Integer>> grid = (List<List<Integer>>) bundle.getSerializable("grid");

        setGrid(grid);

        Map<String, List<List<Integer>>> map = new HashMap<>();
        map.put("board", grid);
        jsonObject = new JSONObject(map);


    }

    public void setGrid(List<List<Integer>> grid){
        plainGrid = new ArrayList<Cell>();
        Cell cell;
/*
        int[] a = {2,4,1,7,9,8,5,3,6,6,9,3,1,2,5,4,7,8,5,8,7,6,4,3,1,2,9,8,1,2,5,3,9,7,6,4,7,6,9,2,1,4,8,5,3,3,5,4,8,6,7,2,9,1,4,7,6,9,5,1,3,8,2,1,2,5,3,8,6,9,4,7,9,3,8,4,7,2,6,1,5};
        for(int i : a) {
            if(i == 0)
                cell = new Cell(i, true);
            else
                cell = new Cell(i, false);
            plainGrid.add(cell);
        }*/

        for(List<Integer> l : grid) {
            for (Integer i : l) {
                if(i == 0)
                    cell = new Cell(i, true);
                else
                    cell = new Cell(i, false);

                plainGrid.add(cell);
            }
            System.out.println(l);
        }
        GridView gridView = (GridView) findViewById(R.id.gvGrid);

        Log.d(TAG, ""+plainGrid.size());
        mAdapter = new SudokuBoardAdapter(getApplicationContext(), plainGrid);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(holder);
    }


    private class Holder implements View.OnClickListener, AdapterView.OnItemClickListener{
        private Button btnSolve;
        private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
        private Button btnDel;
        private int currentCell;

        public Holder(){
            btnSolve = findViewById(R.id.btnSolve);
            btnDel = findViewById(R.id.btnDel);
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
                case(R.id.btnSolve):
                    if(GameController.check(plainGrid)) {
                        simpleChronometer.stop();
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
        }
    }
}