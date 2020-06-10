package it.lomele.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity{

    private Holder holder;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        holder = new Holder();
    }

    class Holder implements View.OnClickListener {
        private Button btn_help;
        private Button btn_newGame;

        private Holder() {
            btn_help = findViewById(R.id.btn_help);
            btn_newGame = findViewById(R.id.btn_newGame);
            btn_help.setOnClickListener(this);
            btn_newGame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btn_help.getId() ) {
                Intent help_intent = new Intent(MenuActivity.this, HelpActivity.class);
                startActivity(help_intent);
            }
            if(v.getId() == btn_newGame.getId()){
                Intent newGame_intent = new Intent(MenuActivity.this, SenzaNomeActivity.class);
                startActivity(newGame_intent);
            }

        }
    }
}





