package it.lomele.sudoku.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

import it.lomele.sudoku.R;
import it.lomele.sudoku.model.Cell;

public class SudokuBoardAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Cell> mGrid;

    public SudokuBoardAdapter(Context context, List<Cell> grid){
        this.mGrid = grid;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return mGrid.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String val = mGrid.get(position).getValue().toString();

        mGrid.get(position).setPosition(position);

        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_cell, null);
        }

        final TextView etValue = (TextView) convertView.findViewById(R.id.etValue);

        // FULL THE GRID
        if(mGrid.get(position).getValue() == 0)
            etValue.setText("");
        else
            etValue.setText(val);

        etValue.setTypeface(null, Typeface.BOLD);
        if(mGrid.get(position).isEditable())
            etValue.setTextColor(ContextCompat.getColor(mContext, R.color.button_background_dark));

        // HIGHLIGHT ROW, COLUMN AND CELLGROUP OF THE SELECTED CELL
        if(mGrid.get(position).isHighlight())
            etValue.setBackground(ContextCompat.getDrawable(mContext,R.drawable.grid_row_border_highlight));
        else
            etValue.setBackground(ContextCompat.getDrawable(mContext,R.drawable.grid_row_border));


        return convertView;
    }


}
