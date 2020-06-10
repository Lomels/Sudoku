package it.lomele.sudoku;

import java.util.ArrayList;
import java.util.List;

public class GameController {

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

}
