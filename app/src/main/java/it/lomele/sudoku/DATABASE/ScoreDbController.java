package it.lomele.sudoku.DATABASE;


import android.content.Context;

import androidx.room.Room;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreDbController {

    Context context;

    public ScoreDbController(Context context){

    }

    public Time convertStrToTime(String string) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Time time = new Time(formatter.parse(string).getTime());
        return time;

    }

    public void insertNewScore(String string, String level) throws ParseException {


        Db score_database = Db.getInstance(context);
        Score score = new Score(convertStrToTime(string),"easy");  //TODO change easy
        score_database.scoreDAO().insert(convertStrToTime(string), level);

    }

    public void find(String string, String level) throws ParseException {
        Db score_database = Db.getInstance(context);
        if(!score_database.scoreDAO().getByLevel(level).equals(null)){
            score_database.scoreDAO().getByTime(convertStrToTime(string));

        }

    }



    //TODO delete()


}
