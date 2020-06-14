package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.lomele.sudoku.R;

public class ScoreActivity extends AppCompatActivity {

    HighscoresActivity highscoresFragment;
    ScoreboardActivity scoreboardFragment;
    int showingFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        createFragment();
        Holder holder = new Holder(findViewById(R.id.clBase));
    }

    public void createFragment(){
        highscoresFragment = new HighscoresActivity();
        scoreboardFragment = new ScoreboardActivity();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, highscoresFragment)
                .commit();

        showingFragment = 1;
    }



    class Holder implements View.OnClickListener{
        private Button btnHigh;
        private Button btnList;

        public Holder(View v){
            btnHigh = v.findViewById(R.id.btnHigh);
            btnList = v.findViewById(R.id.btnList);

            btnHigh.setOnClickListener(this);
            btnList.setOnClickListener(this);

            btnHigh.setEnabled(false);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.btnHigh):
                    btnHigh.setEnabled(false);
                    btnList.setEnabled(true);
                    break;
                case(R.id.btnList):
                    btnHigh.setEnabled(true);
                    btnList.setEnabled(false);
                    break;
            }
            switchFragment();
        }

        public void switchFragment(){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch(showingFragment) {
                case (1):
                    fragmentTransaction.replace(R.id.frameLayout, scoreboardFragment);
                    showingFragment = 2;
                    break;
                case (2):
                    fragmentTransaction.replace(R.id.frameLayout, highscoresFragment);
                    showingFragment = 1;
                    break;
            }

            fragmentTransaction.commit();

        }
    }
}

