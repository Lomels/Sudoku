package it.lomele.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.lomele.sudoku.DATABASE.Score;
import it.lomele.sudoku.DATABASE.ScoreDbController;

public class ScoreboardActivity extends Activity {

    private RecyclerView recyclerView;
    private ScoreboardActivity.ScoreAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        ScoreDbController controller = new ScoreDbController(getApplicationContext());

        List<Score> list = controller.getAll();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.rv_scoreboard);
        RecyclerView.Adapter mAdapter = new ScoreAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }


    public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.Holder> {
        List<Score> mDataset;

        ScoreAdapter(List<Score> myDataset) {
            mDataset = myDataset;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout cl = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_row_scoreboard, parent, false);
            return new Holder(cl);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if(!mDataset.isEmpty())
                holder.tvScore.setText(mDataset.get(position).toString());
            holder.tvScore.setText("No scores available.");
        }

        @Override
        public int getItemCount() {
            if(!mDataset.isEmpty())
                return mDataset.size();
            return 0;
        }


        private class Holder extends RecyclerView.ViewHolder {
            private TextView tvScore;

            public Holder(@NonNull View itemView) {
                super(itemView);
                tvScore = itemView.findViewById(R.id.tv_row);
            }
        }


    }
}
