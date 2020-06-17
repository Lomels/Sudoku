package it.lomele.sudoku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.lomele.sudoku.DATABASE.Score;
import it.lomele.sudoku.DATABASE.ScoreDbController;
import it.lomele.sudoku.R;
import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.GridManager;

public class ScoreboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScoreboardFragment.ScoreAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridView gridView;

    private SudokuBoardAdapter gridAdapter;

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

        gridView = (GridView) rootView.findViewById(R.id.gvGrid);

        return rootView;

    }


    public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.Holder> {
        List<Score> mDataset;

        ScoreAdapter(List<Score> myDataset) {
            if (myDataset != null)
                mDataset = myDataset;
            else
                mDataset = new ArrayList<>();
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
            if (!mDataset.isEmpty()) {
                holder.tvLevel.setText(mDataset.get(position).getLevel());
                holder.tvTime.setText(mDataset.get(position).getTime());
                holder.setBoard(mDataset.get(position).getBoard());
            }else
                holder.tvLevel.setText("No scores available.");
        }

        @Override
        public int getItemCount() {
            if (!mDataset.isEmpty())
                return mDataset.size();
            return 0;
        }


        public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView tvTime;
            private TextView tvLevel;
            private Button btnShow;

            private List<Integer> mBoard;

            public Holder(@NonNull View itemView) {
                super(itemView);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvLevel = itemView.findViewById(R.id.tvLevel);
                btnShow = itemView.findViewById(R.id.btnShow);

                btnShow.setOnClickListener(this);
            }

            public void setBoard(List<Integer> board){ this.mBoard = board;}

            @Override
            public void onClick(View v) {
                List<Cell> cellBoard = GridManager.fromIntArrayToCellArray(mBoard);
                gridAdapter = new SudokuBoardAdapter(getContext(), cellBoard);
                gridView.setAdapter(gridAdapter);

            }
        }
    }
}
