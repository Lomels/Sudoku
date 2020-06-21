package it.lomele.sudoku.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;

import it.lomele.sudoku.R;

public class MenuActivity extends AppCompatActivity {
    private Holder holder;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        holder = new Holder();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        holder.btnNewGame.setVisibility(View.VISIBLE);
        holder.btnScores.setVisibility(View.VISIBLE);
        holder.btnHelp.setVisibility(View.VISIBLE);
        holder.btnEn.setVisibility(View.VISIBLE);
        holder.btnIt.setVisibility(View.VISIBLE);
    }

    class Holder implements View.OnClickListener {
        private Button btnHelp;
        private Button btnNewGame;
        private Button btnScores;
        private Button btnEn;
        private Button btnIt;

        private Holder() {
            btnHelp = findViewById(R.id.btnHelp);
            btnNewGame = findViewById(R.id.btnNewGame);
            btnScores = findViewById(R.id.btnScores);
            btnEn = findViewById(R.id.btnEn);
            btnIt = findViewById(R.id.btnIt);

            btnHelp.setOnClickListener(this);
            btnNewGame.setOnClickListener(this);
            btnScores.setOnClickListener(this);
            btnEn.setOnClickListener(this);
            btnIt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btnHelp.getId() ) {
                Intent helpIntent = new Intent(MenuActivity.this, HelpActivity.class);
                startActivity(helpIntent);
            }
            if(v.getId() == btnNewGame.getId()){
                Intent newGameIntent = new Intent(MenuActivity.this, NewGameActivity.class);
                startActivity(newGameIntent);
            }
            if(v.getId()== btnScores.getId()){
                Intent scoresIntent = new Intent(MenuActivity.this, ScoreActivity.class);
                startActivity(scoresIntent);
            }

            if(v.getId() == btnEn.getId()){
                setAppLocale("en");
            }

            if(v.getId() == btnIt.getId()){
                setAppLocale("it");
            }

        }

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

            Intent menuIntent = new Intent(MenuActivity.this, MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(menuIntent);
        }
    }
}





