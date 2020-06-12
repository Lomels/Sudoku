package it.lomele.sudoku.model;

import java.io.Serializable;

public class Cell implements Serializable {
    private Integer value;
    private int position;
    private int row;
    private int col;
    private int block;
    private boolean editable;
    private boolean highlight;

    public Cell(int value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getBlock(){ return block;}

    public void setBlock(int block){ this.block = block;}

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}
