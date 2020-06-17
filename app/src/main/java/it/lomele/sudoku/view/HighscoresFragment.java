package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import it.lomele.sudoku.DATABASE.Score;
import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;

public class HighscoresFragment extends Fragment {
    ScoreDbController controller;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
         controller = new ScoreDbController(getContext());
         View rootView = layoutInflater.inflate(R.layout.fragment_highscores, viewGroup, false);
         setHighScores(rootView);
         return rootView;

    }

    public void setHighScores(View v) {
        List<String> hardList = new ArrayList<>(3);
        List<String> easyList = new ArrayList<>(3);
        List<String> mediumList = new ArrayList<>(3);

        TextView tvEasy1 = v.findViewById(R.id.tvEasy1);
        TextView tvEasy2 = v.findViewById(R.id.tvEasy2);
        TextView tvEasy3 = v.findViewById(R.id.tvEasy3);

        TextView tvMedium1 = v.findViewById(R.id.tvMedium1);
        TextView tvMedium2 = v.findViewById(R.id.tvMedium2);
        TextView tvMedium3 = v.findViewById(R.id.tvMedium3);

        TextView tvHard1 = v.findViewById(R.id.tvHard1);
        TextView tvHard2 = v.findViewById(R.id.tvHard2);
        TextView tvHard3 = v.findViewById(R.id.tvHard3);

        try {
            controller.createList();
            if (!(easyList = controller.getEasyList()).isEmpty()) {
                tvEasy1.setText(easyList.get(0));
                tvEasy2.setText(easyList.get(1));
                tvEasy3.setText(easyList.get(2));
            }
            if (!(mediumList = controller.getMediumList()).isEmpty()) {
                tvMedium1.setText(mediumList.get(0));
                tvMedium2.setText(mediumList.get(1));
                tvMedium3.setText(mediumList.get(2));
            }

            if(!(hardList = controller.getHardList()).isEmpty()) {
                tvHard1.setText(hardList.get(0));
                tvHard2.setText(hardList.get(1));
                tvHard3.setText(hardList.get(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
