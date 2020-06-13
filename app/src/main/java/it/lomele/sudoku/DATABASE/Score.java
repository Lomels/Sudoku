package it.lomele.sudoku.DATABASE;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Score {
    @ColumnInfo(name = "time")
    public String time;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "level")
    public String level;


    public Score(String time, String level) {
        this.time = time;
        this.level = level;
    }

    @Override
    public String toString(){
        String mString = "Level: "+level+", Time: "+time;
        return mString;
    }
}
