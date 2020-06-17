package it.lomele.sudoku.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import it.lomele.sudoku.R;

public class MenuActivity extends Activity{

    private Holder holder;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        holder = new Holder();
    }

    class Holder implements View.OnClickListener {
        private Button btnHelp;
        private Button btnNewGame;
        private Button btnScores;
        private Button btnSettings;
        private Button btnEn;
        private Button btnIt;

        private Holder() {
            btnHelp = findViewById(R.id.btnHelp);
            btnNewGame = findViewById(R.id.btnNewGame);
            btnScores = findViewById(R.id.btnScores);
            btnSettings = findViewById(R.id.btnSettings);
            btnEn = findViewById(R.id.btnEn);
            btnIt = findViewById(R.id.btnIt);

            btnHelp.setOnClickListener(this);
            btnNewGame.setOnClickListener(this);
            btnScores.setOnClickListener(this);
            btnSettings.setOnClickListener(this);
            btnEn.setOnClickListener(this);
            btnIt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btnHelp.getId() ) {
                Intent help_intent = new Intent(MenuActivity.this, HelpActivity.class);
                startActivity(help_intent);
            }
            if(v.getId() == btnNewGame.getId()){
                Intent newGame_intent = new Intent(MenuActivity.this, NewGameActivity.class);
                startActivity(newGame_intent);
            }
            if(v.getId()== btnScores.getId()){
                Intent scores_intent = new Intent(MenuActivity.this, ScoreActivity.class);
                startActivity(scores_intent);
            }

            if(v.getId() == btnEn.getId()){
                setAppLocale("en");
                Intent menu_intent = new Intent(MenuActivity.this, MenuActivity.class);
                startActivity(menu_intent);
            }

            if(v.getId() == btnIt.getId()){
                setAppLocale("it");
                Intent menu_intent = new Intent(MenuActivity.this, MenuActivity.class);
                startActivity(menu_intent);

            }





        } //TODO SWITCH AND CHANGE LINK BETWEEN BTN_SETTINGS AND SCOREBOARDACTIVITY

        private void setAppLocale(String localeCode){
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                conf.setLocale(new Locale(localeCode.toLowerCase()));
            }else{
                conf.locale = new Locale(localeCode.toLowerCase());
            }
            res.updateConfiguration(conf, dm);
        }
    }
}





