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
    private List<Cell> solution;

    public GameService(Handler handler){
        this.mHandler = handler;
    }

    /*
    Creates and runs the SolvingThread
     */
    public void solve(Riddle toSolve){
        SolvingThread thread = new SolvingThread(toSolve);
        thread.start();
    }

    /*
    Creates and runs the HintThread
     */
    public void hint(List<Cell> toSolve){
        HintThread hintThread = new HintThread(toSolve);
        hintThread.start();
    }

    /*
     It checks if the solution provided by the user is correct
     */
    public static boolean check(List<Cell> userSolution, List<Cell> correctSolution) {
        for(int i=0; i<userSolution.size(); i++){
            if(!userSolution.get(i).getValue()
                    .equals(correctSolution.get(i).getValue()))
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
            //Creates a list of cells taken from the solution that aren't in the user current board
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

            // Gets a random Cell from the missingCells list
            while(!hintFound) {
                hintIndex = random.nextInt(80);
                if ((mHint = missingCells.get(hintIndex)).getValue() != 0)
                    hintFound = true;
            }

            // Adds it to the current board
            userAttempt.remove(hintIndex);
            userAttempt.add(hintIndex, mHint);

            // Sends it to the UIThread
            Bundle bundle = new Bundle();
            bundle.putSerializable("board",(Serializable) userAttempt);
            Message msg = mHandler.obtainMessage(Constant.HINT_MSG);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }

    private class SolvingThread extends Thread {
        private Riddle toSolve;

        public SolvingThread(Riddle grid){
            this.toSolve = grid;
        }

        @Override
        public void run() {
            // Creates the solution
            Solver solver = new Solver(toSolve);
            List<GameMatrix> solutions = solver.solve();

            // Converts it into the correct format
            solution = GridManager.fromGameMatrixToCellArray(solutions.get(0));

            // Sends it to the UIThread
            Bundle bundle = new Bundle();
            bundle.putSerializable("solved", (Serializable) solution);
            Message msg = mHandler.obtainMessage(Constant.SOLUTION_MSG);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }

    }
}

