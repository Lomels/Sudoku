package it.lomele.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import it.lomele.sudoku.R;
import it.lomele.sudoku.utils.Constant;
import java.io.Serializable;
import java.util.List;

public class NewGameActivity extends AppCompatActivity {
    //DEBUG
    private static final String TAG = "MainActivity";

    protected Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senzanome);
        holder = new Holder();
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
            int difficulty = 1;
            switch (v.getId()){
                case(R.id.btnEasy):
                    difficulty = Constant.DIFFICULTY_EASY;
                    break;
                case(R.id.btnMedium):
                    difficulty = Constant.DIFFICULTY_MEDIUM;
                    //TODO IMPLEMENTARE DIFFICOLTA
                    break;
                case(R.id.btnHard):
                    difficulty = Constant.DIFFICULTY_HARD;
                    break;
            }

            Intent i = new Intent(getApplicationContext(), GameActivity.class);
            i.putExtra("difficulty", difficulty);
            startActivity(i);
        }

        public void setError(String e){
            tvSudoku.setText(e);
        }
    }
}
