package it.lomele.sudoku.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.lomele.sudoku.R;

public class HelpActivity extends AppCompatActivity {

    Rules1Fragment rules1Fragment;
    Rules2Fragment rules2Fragment;
    int showingFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        createFragment();
        Holder holder = new Holder(findViewById(R.id.clBase));
    }

    public void createFragment(){
        rules1Fragment = new Rules1Fragment();
        rules2Fragment = new Rules2Fragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, rules1Fragment)
                .commit();

        showingFragment = 1;
    }



    class Holder implements View.OnClickListener{
        private Button btnBack;
        private Button btnNext;

        public Holder(View v){
            btnBack = v.findViewById(R.id.btnBack);
            btnNext = v.findViewById(R.id.btnNext);

            btnBack.setOnClickListener(this);
            btnNext.setOnClickListener(this);

            btnBack.setEnabled(false);
            btnBack.setBackgroundColor(getColor(R.color.colorButtonDarkDisabled));
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.btnBack):
                    btnBack.setEnabled(false);
                    btnBack.setBackgroundColor(getColor(R.color.colorButtonDarkDisabled));
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(getColor(R.color.colorButtonDark));
                    break;
                case(R.id.btnNext):
                    btnBack.setEnabled(true);
                    btnBack.setBackgroundColor(getColor(R.color.colorButtonDark));
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(getColor(R.color.colorButtonDarkDisabled));
                    break;
            }
            switchFragment();
        }

        public void switchFragment(){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch(showingFragment) {
                case (1):
                    fragmentTransaction.replace(R.id.frameLayout, rules2Fragment);
                    showingFragment = 2;
                    break;
                case (2):
                    fragmentTransaction.replace(R.id.frameLayout, rules1Fragment);
                    showingFragment = 1;
                    break;
            }

            fragmentTransaction.commit();

        }
    }
}




