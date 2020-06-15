package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.List;

import it.lomele.sudoku.DATABASE.Score;
import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;

public class HighscoresActivity extends Fragment {
    ScoreDbController controller;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
         controller = new ScoreDbController(getContext());
         View rootView = layoutInflater.inflate(R.layout.fragment_highscores, viewGroup, false);
         setHighScores(rootView);
         return rootView;

    }

    public void setHighScores(View v){
        List<Score> hardList;
        List<Score> easyList =  null;
        List<Score> mediumList;
        TextView tvEasy1 = v.findViewById(R.id.tvEasy1);
        TextView tvEasy2 = v.findViewById(R.id.tvEasy2);
        TextView tvEasy3 = v.findViewById(R.id.tvEasy3);

        try {
            controller.createList();
            controller.getEasyList();
            tvEasy1.setText(easyList.get(0).getTime());
            tvEasy2.setText(easyList.get(1).getTime());
            tvEasy3.setText(easyList.get(2).getTime());


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }




}
