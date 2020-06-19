package it.lomele.sudoku.database;


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

    private ScoreDatabase scoreDatabase;
    private List<Score> scores;

    public ScoreDbController(Context context){
        scoreDatabase = ScoreDatabase.getInstance(context);
    }

    /*
    Converts a time expressed in ms, to a String with format HH:MM:SS
     */
    public static String convertLongToParsedString(long time) {
        String myTime;
        int seconds = (int) time / 1000;

        if (seconds < 60){
            myTime = "00:00:"+seconds;
            return myTime;
        }

        int minutes = seconds / 60;
        seconds = seconds % 60;

        if(minutes < 60){
            myTime = "00:"+minutes +":"+seconds;
            return  myTime;
        }

        return null;
    }

    /*
    Inserts a new score in the Database
    */

    public void insertNewScore(String time, int level, List<Cell> board, int result){
        List<Integer> mBoard = GridManager.fromCellArrayToIntArray(board);
        Score score = new Score(time, level, mBoard, result);
        scoreDatabase.scoreDAO().insert(score);
        Log.d(TAG, "Inserted new score: "+score.toString()+"with time: "+time);
    }

    /*
    Gets every score from the Database
     */
    public List<Score> getAll(){
        scores = scoreDatabase.scoreDAO().getAll();
        if(scores.isEmpty()){
            Log.d(TAG, "Empty Database");
            return null;
        }
        Log.d(TAG, "Score found: "+scores.get(0).toString());
        return scores;
    }

    public int getWins(){
        return scoreDatabase.scoreDAO().countWin();
    }

    public int getSize(){
        return scoreDatabase.scoreDAO().size();
    }


    /*It creates a list of all the games time,
    / one for each level of difficulty and it sorts them.
    */

    public void createList(){
        List<Score> allScore = getAll();
        if(allScore == null)
            return;

        hardList= new ArrayList<>();
        mediumList = new ArrayList<>();
        easyList = new ArrayList<>();

        int j = 0;
        int k = 0;
        int z = 0;

        for (Score s : allScore){
            if(s.getResult() == 1){     // If the result of the game is a Win, take the time
                switch(s.getLevel()) {  // Create 3 different list, one for each level of difficulty
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
        }

        // Sort each list
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
