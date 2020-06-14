package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import it.lomele.sudoku.R;

public class HighscoresActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.activity_highscores, viewGroup, false);
    }

  /*  class Holder implements View.OnClickListener{

        private Button btnList;

        public Holder(){
            btnList = findViewById(R.id.btnList);
            btnList.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), ScoreboardActivity.class);
            startActivity(i);
        }
    }*/
}
