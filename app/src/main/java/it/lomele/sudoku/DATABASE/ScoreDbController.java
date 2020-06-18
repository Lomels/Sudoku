package it.lomele.sudoku.DATABASE;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.lomele.sudoku.model.Cell;
import it.lomele.sudoku.utils.Constant;
import it.lomele.sudoku.utils.GridManager;

public class ScoreDbController {
    //DEBUG
    private static final String TAG = "ScoreDbController";

    private List<String> hardList;
    private List<String> mediumList;
    private List<String> easyList;

    Context context;
    private Db score_database;
    private List<Score> scores;

    public ScoreDbController(Context context){
        score_database = Db.getInstance(context);
    }

    public static String convertLongToParsedString(long time) {
        String myTime;
        int seconds = (int) time / 1000;

        if (seconds < 60){
            myTime = "00:00:"+seconds;
            Log.d(TAG, "MyTime = "+myTime);
            return myTime;
        }

        int minutes = seconds / 60;
        seconds = seconds % 60;

        if(minutes < 60){
            myTime = "00:"+minutes +":"+seconds;
            Log.d(TAG, "MyTime = "+myTime);
            return  myTime;
        }

        return null;
    }

    public void insertNewScore(String time, int level, List<Cell> board){
        List<Integer> mBoard = GridManager.fromCellArrayToIntArray(board);
        Score score = new Score(time, level, mBoard);
        score_database.scoreDAO().insert(score);
        Log.d(TAG, "Inserted new score: "+score.toString()+"with time: "+time);
    }

    public List<Score> getAll(){
        scores = score_database.scoreDAO().getAll();
        if(scores.isEmpty()){
            Log.d(TAG, "Empty Database");
            return null;
        }
        Log.d(TAG, "Score found: "+scores.get(0).toString());
        return scores;
    }

    public void createList(){
        List<Score> allScore = getAll();
        if(allScore == null)
            return;

        hardList= new ArrayList<>();
        mediumList = new ArrayList<>();
        easyList = new ArrayList<>();

        int i;
        int j = 0;
        int k = 0;
        int z = 0;

        for (Score s : allScore){
            switch(s.getLevel()) {
                case (Constant.DIFFICULTY_HARD):
                    hardList.add(j, s.getTime());
                    j++;
                    break;
                case (Constant.DIFFICULTY_MEDIUM):
                    mediumList.add(k, s.getTime());
                    k++;
                    break;
                case (Constant.DIFFICULTY_EASY):
                    easyList.add(z, s.getTime());
                    z++;
                    break;
            }
        }

        Collections.sort(easyList);
        Collections.sort(mediumList);
        Collections.sort(hardList);
    }

    public List<String> getHardList() {
        return hardList;
    }

    public List<String> getMediumList() {
        return mediumList;
    }

    public List<String> getEasyList() {
        return easyList;
    }
}
