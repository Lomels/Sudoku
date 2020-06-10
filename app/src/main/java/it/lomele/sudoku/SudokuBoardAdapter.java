package it.lomele.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

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

        if(mGrid.get(position).getValue() == 0){
            etValue.setText("");
        }else{
            etValue.setText(val);
        }
        if(!mGrid.get(position).isEditable())
            etValue.setTypeface(null, Typeface.BOLD);
        else
            etValue.setTextColor(Color.GREEN);



        return convertView;
    }

}
