package it.lomele.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class NewGameActivity extends AppCompatActivity {
    //DEBUG
    private static final String TAG = "NewGameActivity";

    protected Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzanome);
        holder = new Holder();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_MAIN){
            if(resultCode == Constant.RESULT_SUDOKU_BOARD_SERVICE){
                Bundle bundle = data.getBundleExtra("bundle");
                List<List<Integer>> grid = (List<List<Integer>>) bundle.getSerializable("grid");
                holder.next(grid);
            }
        }
    }


    private class Holder implements View.OnClickListener{
        private TextView tvSudoku;
        private Button btnEasy;
        private Button btnMedium;
        private Button btnHard;

        public Holder(){
            tvSudoku = findViewById(R.id.tvSudoku);
            btnEasy = findViewById(R.id.btnEasy);
            btnMedium = findViewById(R.id.btnMedium);
            btnHard = findViewById(R.id.btnHard);

            btnEasy.setOnClickListener(this);
            btnMedium.setOnClickListener(this);
            btnHard.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SudokuBoardGenerator.class);
            switch (v.getId()){
                case(R.id.btnEasy):
                    intent.putExtra("difficulty", Constant.DIFFICULTY_EASY);
                case(R.id.btnMedium):
                    intent.putExtra("difficulty", Constant.DIFFICULTY_MEDIUM);
                    break;
                case(R.id.btnHard):
                    intent.putExtra("difficulty", Constant.DIFFICULTY_HARD);
                    break;
            }

            intent.putExtra("request", Constant.REQUEST_CODE_MAIN);
            startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
        }

        public void next(List<List<Integer>> grid){
            Intent i = new Intent(getApplicationContext(), GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("grid", (Serializable) grid);
            i.putExtra("bundle", bundle);
            startActivity(i);
        }

        public void setError(String e){
            tvSudoku.setText(e);
        }
    }
}
