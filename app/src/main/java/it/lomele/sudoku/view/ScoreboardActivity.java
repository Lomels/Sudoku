package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.lomele.sudoku.DATABASE.Score;
import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;

public class ScoreboardActivity extends Fragment {

    private RecyclerView recyclerView;
    private ScoreboardActivity.ScoreAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = layoutInflater.inflate(R.layout.fragment_scoreboard, viewGroup, false);

        ScoreDbController controller = new ScoreDbController(getContext());
        List<Score> list = controller.getAll();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_scoreboard);
        RecyclerView.Adapter mAdapter = new ScoreAdapter(list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;

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
            else
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

    /*class Holder implements View.OnClickListener {
            private Button btnHigh;

            public Holder(){
                btnHigh = findViewById(R.id.btnHigh2);
                btnHigh.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HighscoresActivity.class);
                startActivity(i);
            }
        }*/
}
