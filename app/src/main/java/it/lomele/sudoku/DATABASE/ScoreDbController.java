package it.lomele.sudoku.DATABASE;


import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreDbController {
    //DEBUG
    private static final String TAG = "ScoreDbController";

    Context context;
    private Db score_database;
    private List<Score> scores;

    public ScoreDbController(Context context){
        score_database = Db.getInstance(context);
    }

    public Time convertStrToTime(String string) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(formatter.parse(string).getTime());
        return time;

    }

    public void insertNewScore(String time, String level) throws ParseException {
        Score score = new Score(time, level);
        score_database.scoreDAO().insert(score);
        Log.d(TAG, "Inserted new score: "+score.toString()+"with time: "+time);
    }

    public void getByLevel(String string, String level) throws ParseException {
        if(!score_database.scoreDAO().getByLevel(level).equals(null)){
            //score_database.scoreDAO().getByTime(convertStrToTime(string));

        }
    }

    public List<Score> getAll(){
        scores = score_database.scoreDAO().getAll();
        if(scores.equals(null)){
            Log.d(TAG, "Empty Database");
            return null;
        }
        Log.d(TAG, "Score founded: "+scores.get(0).toString());
        return scores;
    }

    //TODO delete()


}
