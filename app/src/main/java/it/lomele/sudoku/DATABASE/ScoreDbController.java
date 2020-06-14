package it.lomele.sudoku.DATABASE;


import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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


    private int partition(List<Score> list, int low, int high) throws ParseException {

        LocalTime pivot = LocalTime.parse(list.get(high).getTime());
        int j;
        int index = (low-1); // indice dell'elemento più piccolo. -1 all'inizio
        for (j=low; j<high; j++){

// se elemento corrente minore di pivot
            LocalTime time = LocalTime.parse(list.get(j).getTime());
            if (time.isBefore(pivot)){

                index++;
// scambia arr[index] and arr[j]
                Score temp = list.get(index);
                list.set(index, list.get(j));
                list.set(j, temp);
            }
        }
// scambia arr[index+1] e arr[high] (o pivot)
        Score temp = list.get(index + 1);
        list.set(index + 1, list.get(high));
        list.set(high, temp);
        return index+1;
    }
    /* La funzione principale che implementa QuickSort()
    arr[] --> array da ordinare,
    low --> indice iniziale,
    high --> indice finale */
    public void sort(List<Score> list, int low, int high) throws ParseException {
        if (low < high)
        {
/* pi è l'indice di partizionamento, arr[pi] è ora al
               posto giusto */
            int pi = partition(list, low, high);
// Ordina ricorsivamente gli elementi prima di
// partition e dopo partition
            sort(list, low, pi-1);
            sort(list, pi+1, high);
        }
    }





    public void stamp() throws ParseException {
        List<Score> hardList= null;
        List<Score> mediumList = null;
        List<Score> easyList = null;
        List<Score> allScore = getAll();
        int i;
        int j = 0;
        int k = 0;
        int z = 0;

        for(i = 0; i < allScore.size(); i++) {

            switch(allScore.get(i).getLevel()) {
                case ("hard"):
                    hardList.set(j, allScore.get(i));
                    j++;
                    break;
                case ("medium"):
                    mediumList.set(k, allScore.get(i));
                    k++;
                    break;
                case ("easy"):
                    easyList.set(z, allScore.get(i));
                    z++;
                    break;
            }

        }

        sort(hardList, 0, hardList.size()-1);
        sort(mediumList, 0, mediumList.size()-1);
        sort(easyList, 0, easyList.size()-1);





    }


}
