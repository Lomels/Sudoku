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
    AboutUsFragment fragment;


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
        holder.btnInfo.setVisibility(View.VISIBLE);
        holder.btnEn.setVisibility(View.VISIBLE);
        holder.btnIt.setVisibility(View.VISIBLE);
    }

    class Holder implements View.OnClickListener {
        private Button btnHelp;
        private Button btnNewGame;
        private Button btnScores;
        private Button btnInfo;
        private Button btnEn;
        private Button btnIt;

        private Holder() {
            btnHelp = findViewById(R.id.btnHelp);
            btnNewGame = findViewById(R.id.btnNewGame);
            btnScores = findViewById(R.id.btnScores);
            btnInfo = findViewById(R.id.btnInfo);
            btnEn = findViewById(R.id.btnEn);
            btnIt = findViewById(R.id.btnIt);

            btnHelp.setOnClickListener(this);
            btnNewGame.setOnClickListener(this);
            btnScores.setOnClickListener(this);
            btnInfo.setOnClickListener(this);
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

            if(v.getId() == btnInfo.getId()){
                btnNewGame.setVisibility(View.GONE);
                btnScores.setVisibility(View.GONE);
                btnHelp.setVisibility(View.GONE);
                btnInfo.setVisibility(View.GONE);
                btnEn.setVisibility(View.GONE);
                btnIt.setVisibility(View.GONE);

                AboutUsFragment fragment = new AboutUsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frameLayoutInfo,fragment)
                        .addToBackStack("fragment")
                        .commit();

            }

            if(v.getId() == btnEn.getId()){
                setAppLocale("en");
            }

            if(v.getId() == btnIt.getId()){
                setAppLocale("it");
            }





        } //TODO SWITCH AND INFO BUTTON

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

            Intent menu_intent = new Intent(MenuActivity.this, MenuActivity.class);
            menu_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(menu_intent);
        }
    }
}





