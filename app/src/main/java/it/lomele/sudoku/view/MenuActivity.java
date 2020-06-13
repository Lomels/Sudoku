package it.lomele.sudoku.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.lomele.sudoku.R;
import it.lomele.sudoku.ScoreboardActivity;
import it.lomele.sudoku.ScoresActivity;

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
        private Button btn_scores;
        private Button btn_settings;

        private Holder() {
            btn_help = findViewById(R.id.btn_help);
            btn_newGame = findViewById(R.id.btn_newGame);
            btn_scores = findViewById(R.id.btn_scores);
            btn_settings = findViewById(R.id.btn_settings);
            btn_help.setOnClickListener(this);
            btn_newGame.setOnClickListener(this);
            btn_scores.setOnClickListener(this);
            btn_settings.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btn_help.getId() ) {
                Intent help_intent = new Intent(MenuActivity.this, HelpActivity.class);
                startActivity(help_intent);
            }
            if(v.getId() == btn_newGame.getId()){
                Intent newGame_intent = new Intent(MenuActivity.this, NewGameActivity.class);
                startActivity(newGame_intent);
            }
            if(v.getId()== btn_scores.getId()){
                Intent scores_intent = new Intent(MenuActivity.this, ScoresActivity.class);
                startActivity(scores_intent);
            }
            if(v.getId() == btn_settings.getId()){
                Intent setting_intent = new Intent(MenuActivity.this, ScoreboardActivity.class);
                startActivity(setting_intent);
            }

        } //TODO SWITCH AND CHANGE LINK BETWEEN BTN_SETTINGS AND SCOREBOARDACTIVITY
    }
}





