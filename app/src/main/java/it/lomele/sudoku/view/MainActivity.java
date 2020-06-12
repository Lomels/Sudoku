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
        private Button btn_start;


        public Holder() {
            btn_start = findViewById(R.id.btn_start);
            btn_start.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            if(v.getId() == btn_start.getId()){
                // Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                Intent menu_intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(menu_intent);
            }


        }
    }



}
