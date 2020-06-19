package it.lomele.sudoku.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import it.lomele.sudoku.R;

public class MainActivity extends AppCompatActivity {

    private Holder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holder = new Holder();
    }

    class Holder implements View.OnClickListener {
        private Button btnStart;


        public Holder() {
            btnStart = findViewById(R.id.btnStart);
            btnStart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btnStart.getId()){
                Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(menuIntent);
            }
        }
    }



}
