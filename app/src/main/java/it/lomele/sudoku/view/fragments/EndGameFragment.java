package it.lomele.sudoku.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import it.lomele.sudoku.R;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.view.MenuActivity;
import it.lomele.sudoku.view.NewGameActivity;
import it.lomele.sudoku.view.ScoreActivity;

public class EndGameFragment extends Fragment {
    private int mResult;
    private String mAttempts;
    private String mHints;
    private String mTime;
    private int mLevel;

    public EndGameFragment(int result, Bundle bundle){
        this.mResult = result;
        this.mAttempts = bundle.getString("attempts");
        this.mLevel = bundle.getInt("level");
        this.mHints = bundle.getString("hints");
        this.mTime = bundle.getString("time");
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View rootView = layoutInflater.inflate(R.layout.fragment_endgame, viewGroup, false);

        setRecapPage(rootView);
        Holder holder = new Holder(rootView);
        return rootView;

    }

    public void setRecapPage(View v){
        TextView tvResult = v.findViewById(R.id.tvResult);
        TextView tvHints = v.findViewById(R.id.tvHints);
        TextView tvAttempts = v.findViewById(R.id.tvAttempts);
        TextView tvLevel = v.findViewById(R.id.tvLevel);
        TextView tvTime = v.findViewById(R.id.tvTime);

        if(mResult == 1){
            tvResult.setText(getString(R.string.toast_win));

        }else if(mResult == 0){
            tvResult.setText(getString(R.string.toast_lose));
        }

        tvAttempts.setText(mAttempts);
        tvHints.setText(mHints);

        switch(mLevel){
            case(Constant.DIFFICULTY_EASY):
                tvLevel.setText(getString(R.string.button_easy));
                break;
            case(Constant.DIFFICULTY_MEDIUM):
                tvLevel.setText(getString(R.string.button_medium));
                break;
            case(Constant.DIFFICULTY_HARD):
                tvLevel.setText(getString(R.string.button_hard));
        }
        tvTime.setText(mTime);
    }

    private class Holder implements View.OnClickListener{
        private Button btnNew;
        private Button btnScores;
        private Button btnHome;

        public Holder(View v){
            btnHome = v.findViewById(R.id.btnHome);
            btnNew = v.findViewById(R.id.btnNew);
            btnScores = v.findViewById(R.id.btnScores);

            btnNew.setOnClickListener(this);
            btnHome.setOnClickListener(this);
            btnScores.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i;
            switch(v.getId()){
                case(R.id.btnHome):
                    i = new Intent(getContext(), MenuActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
                case(R.id.btnNew):
                    i = new Intent(getContext(), NewGameActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
                case(R.id.btnScores):
                    i = new Intent(getContext(), ScoreActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
            }
        }
    }
}
