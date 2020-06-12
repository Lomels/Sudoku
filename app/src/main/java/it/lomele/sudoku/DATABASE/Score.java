package it.lomele.sudoku.DATABASE;




import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.sql.Time;


@Entity
public class Score {
    @ColumnInfo(name = "time")
    public Time time;

    @ColumnInfo(name = "level")
    public String level;


    public Score(Time time, String level) {
        this.time = time;
        this.level = level;
    }
}
