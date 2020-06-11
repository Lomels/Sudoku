package it.lomele.sudoku;

import java.util.ArrayList;
import java.util.List;

public class GameController{

    public static boolean check(List<Cell> list) {
        List<Integer> values = new ArrayList<Integer>(9);

        //Check rows
        for(int i=0; i<list.size(); i+=9) {
            System.out.println("row: ");
            for(int j=i; j<i+9; j++) {

                if(values.contains(list.get(j).getValue()))
                    return false;
                values.add(list.get(j).getValue());
            }
            System.out.println(values);
            values.clear();
        }

        //Check columns
        for(int i=0; i<9; i++) {
            System.out.println("col: ");
            for(int j=i; j<list.size() ; j += 9) {
                if(values.contains(list.get(j).getValue()))
                    return false;
                values.add(list.get(j).getValue());
            }
            System.out.println(values);
            values.clear();
        }


        //Check blocks
        for(int i=0; i<list.size(); i += list.size()/3) {
            for(int k=i; k<i+9; k +=3) {
                System.out.println("Block: ");
                for(int j=k; j<=k+18; j += 9) {
                    for(int z=j; z<j+3; z++) {
                        if(values.contains(list.get(z).getValue()))
                            return false;
                        values.add(list.get(z).getValue());
                    }


                }
                System.out.println(values);
                values.clear();
            }
        }

        return true;
    }

    public static List<List<Cell>> fromIntMatrixToCellMatrix(List<List<Integer>> list){
        Cell cell;
        List<List<Cell>> matrix = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            List<Cell> temp = new ArrayList<>();
            for(int j=0; j<list.get(i).size(); j++){

                if(list.get(i).get(j) != 0)
                    cell = new Cell(list.get(i).get(j), true);
                else
                    cell = new Cell(list.get(i).get(j), false);

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
            for(Cell c : l){
                arrayList.add(c);
            }
        }

        return arrayList;
    }

    public static List<List<Cell>> fromCellArrayToCellMatrix(List<Cell> array){
        List<List<Cell>> matrix = new ArrayList(9);

        for(int i=0; i<matrix.size(); i++){
            int count = 0;
            for(Cell c : array) {
                if(count<9) {
                    matrix.get(i).add(c);
                    count++;
                }else{
                    break;
                }
            }
        }

        System.out.println("MATRICE: "+matrix);

        return matrix;
    }

    public static List<Cell> fromIntMatrixToCellArray(List<List<Integer>> matrix){
        Cell cell;
        List<Cell> arrayList = new ArrayList<>();
        for(List<Integer> l : matrix) {
            for (Integer i : l) {
                if(i == 0)
                    cell = new Cell(i, true);
                else
                    cell = new Cell(i, false);

                arrayList.add(cell);
            }
        }

        return arrayList;
    }


}
