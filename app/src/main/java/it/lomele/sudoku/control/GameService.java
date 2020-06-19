package it.lomele.sudoku.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import de.sfuhrm.sudoku.Solver;
import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.utils.GridManager;

public class GameService{
    // DEBUG
    private static final String TAG = "GameService";

    private final Handler mHandler;
    private HintThread hintThread = null;
    private List<Cell> solution;

    public GameService(Handler handler){
        this.mHandler = handler;
    }

    public void solve(Riddle toSolve){
        SolvingThread thread = new SolvingThread(toSolve);
        thread.start();
    }

    public void hint(List<Cell> toSolve){
        // If a thread is already looking for a hint, stop it and start a new one
        /*if(hintThread != null)
            hintThread.delete();
*/        hintThread = new HintThread(toSolve);
        hintThread.start();

    }

    // It checks if the solution provided by the user is correct
    public static boolean check(List<Cell> userSolution, List<Cell> correctSolution) {
        for(int i=0; i<userSolution.size(); i++){
            if(!userSolution.get(i).getValue().equals(correctSolution.get(i).getValue()))
                return false;
        }
        return true;
    }

    private class HintThread extends  Thread{
        private List<Cell> userAttempt;
        private List<Cell> missingCells = new ArrayList<>();

        public HintThread(List<Cell> array){
            this.userAttempt = array;
        }

        @Override
        public void run(){
            for(int i=0; i<userAttempt.size(); i++){
                if(!userAttempt.get(i).getValue().equals(solution.get(i).getValue()))
                    missingCells.add(i, solution.get(i));
                else
                    missingCells.add(i, new Cell(0, false));
            }

            Boolean hintFound = false;
            Cell mHint = null;
            Random random = new Random();
            int hintIndex = 0;

            while(!hintFound) {
                hintIndex = random.nextInt(80);
                if ((mHint = missingCells.get(hintIndex)).getValue() != 0)
                    hintFound = true;
            }

            userAttempt.remove(hintIndex);
            userAttempt.add(hintIndex, mHint);

            Log.d(TAG, "HintThread prima del messaggio: "+ userAttempt.toString());

            Bundle bundle = new Bundle();
            bundle.putSerializable("board",(Serializable) userAttempt);
            Message msg = mHandler.obtainMessage(Constant.HINT_MSG);
            msg.setData(bundle);
            mHandler.sendMessage(msg);

            Log.d(TAG, "HintThread: hint created");

        }


    }

    private class SolvingThread extends Thread {
        private Riddle toSolve;

        public SolvingThread(Riddle grid){
            this.toSolve = grid;
        }

        @Override
        public void run() {
            Solver solver = new Solver(toSolve);
            List<GameMatrix> solutions = solver.solve();

            solution = GridManager.fromGameMatrixToCellArray(solutions.get(0));
            for (Cell c : solution) {
                System.out.println(c.isEditable());
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("solved", (Serializable) solution);
            Message msg = mHandler.obtainMessage(Constant.SOLUTION_MSG);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
            Log.d(TAG, "SolvingThread: finish, sending broadcast");
        }

    }
}

