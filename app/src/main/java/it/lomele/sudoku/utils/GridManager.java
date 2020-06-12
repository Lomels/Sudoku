package it.lomele.sudoku.utils;

import java.util.ArrayList;
import java.util.List;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import it.lomele.sudoku.model.Cell;

public class GridManager {

    public static List<List<Cell>> fromIntMatrixToCellMatrix(List<List<Integer>> list){
        Cell cell;
        List<List<Cell>> matrix = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            List<Cell> temp = new ArrayList<>();
            for(int j=0; j<list.get(i).size(); j++){
                if(list.get(i).get(j) != 0)
                    cell = new Cell(list.get(i).get(j), false);
                else
                    cell = new Cell(list.get(i).get(j), true);

                cell.setRow(i);
                cell.setCol(j);
                temp.add(cell);
            }
            matrix.add(temp);
        }

        return matrix;
    }

    public static List<Cell> fromCellMatrixToCellArray(List<List<Cell>> matrix){
        List<Cell> arrayList = new ArrayList<>();
        for(List<Cell> l : matrix){
            arrayList.addAll(l);
        }

        return arrayList;
    }

    public static List<List<Cell>> fromCellArrayToCellMatrix(List<Cell> array){
        ArrayList<List<Cell>> matrix = new ArrayList<List<Cell>>(9);

        for(int i=0; i<9; i++){
            List<Cell> temp = new ArrayList<>();
            int count = 0;
            for(Cell c : array) {
                if(count<9) {
                    temp.add(c);
                    count++;
                }else{
                    break;
                }
            }
            matrix.add(i,temp);
        }

        for(List<Cell> l : matrix){
            for(Cell c : l){
                System.out.println(c.getValue());
            }
        }

        return matrix;
    }

    public static List<Cell> fromIntMatrixToCellArray(List<List<Integer>> matrix) {
        Cell cell;
        List<Cell> arrayList = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 0)
                    cell = new Cell(matrix.get(i).get(j), true);
                else
                    cell = new Cell(matrix.get(i).get(j), false);

                cell.setBlock(checkBlock(i,j));
                cell.setRow(i);
                cell.setCol(j);
                arrayList.add(cell);
            }
        }

        return arrayList;
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
}
