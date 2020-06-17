package it.lomele.sudoku.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;

public class Rules1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        return layoutInflater.inflate(R.layout.fragment_rules1, viewGroup, false);

    }

}
