package it.lomele.sudoku.utils;

import java.util.ArrayList;
import java.util.List;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import it.lomele.sudoku.model.Cell;

public class GridManager {

    public static List<Integer> fromCellArrayToIntArray(List<Cell> board){
        List<Integer> newBoard = new ArrayList<>();

        for(Cell c : board){
            newBoard.add(c.getValue());
        }

        return newBoard;
    }

    public static List<Cell> fromIntArrayToCellArray(List<Integer> board){
        List<Cell> newList = new ArrayList<>();
        Cell cell;

        for(Integer i : board){
            cell = new Cell(i, true);
            newList.add(cell);
        }

        return newList;

    }

    public static List<Cell> fromGameMatrixToCellArray(GameMatrix matrix){
        // FORMATTING IN List<Cell>, EASIER TO HANDLE FOR GRIDVIEW
        List<Cell> array = new ArrayList<Cell>();
        Cell cell;

        for(int row=0; row<matrix.SIZE; row++){
            for(int column=0; column<matrix.SIZE; column++) {
                int val = matrix.get(row, column);
                if (val == 0)
                    cell = new Cell(val, true);
                else
                    cell = new Cell(val, false);

                cell.setBlock(checkBlock(row, column));
                cell.setRow(row);
                cell.setCol(column);
                array.add(cell);
            }
        }

        return array;
    }

    public static int checkBlock(int row, int col){
        int block = 0;
        if(col < 3) {
            if (row < 3)
                block = 1;
            else if (row < 6)
                block = 2;
            else
                block = 3;
        }else if(col < 6) {
            if (row < 3)
                block = 4;
            else if (row < 6)
                block = 5;
            else
                block = 6;
        }else{
            if (row < 3)
                block = 7;
            else if (row < 6)
                block = 8;
            else
                block = 9;
        }

        return block;
    }

    public static List<Cell> highlight(List<Cell> array, int row, int col, int block){
       for(Cell cell : array){
           if(cell.getRow() == row || cell.getCol() == col || cell.getBlock() == block)
               cell.setHighlight(true);
           else
               cell.setHighlight(false);
       }
        return array;
    }

}
