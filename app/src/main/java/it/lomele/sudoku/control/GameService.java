package it.lomele.sudoku.control;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import de.sfuhrm.sudoku.Solver;
import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.utils.GridManager;

public class GameService implements Runnable {
    // DEBUG
    private static final String TAG = "GameService";


    private Riddle mRiddle;

    public GameService(Riddle riddle){
        this.mRiddle = riddle;
    }

    public static boolean check(List<Cell> userSolution, List<Cell> correctSolution) {
        /*List<Integer> values = new ArrayList<Integer>(9);

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
        }*/

        for(int i=0; i<userSolution.size(); i++){
            if(!userSolution.get(i).getValue().equals(correctSolution.get(i).getValue()))
                return false;
        }

        return true;
    }

    @Override
    public void run() {
        Solver solver = new Solver(mRiddle);
        List<GameMatrix> solutions = solver.solve();

        List<Cell> mSolution = GridManager.fromGameMatrixToCellArray(solutions.get(0));
        for(Cell c : mSolution){
            System.out.println(c.isEditable());
        }
        Intent i = new Intent(Constant.RESULT);
        Bundle bundle = new Bundle();
        bundle.putSerializable("solved", (Serializable) mSolution);
        i.putExtra("bundle", bundle);

        Log.d(TAG, "Thread: finish, sending broadcast");
        sendBroadcast(i);
    }

}
