package it.lomele.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Integer value;
    private int position;
    private boolean editable;

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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
