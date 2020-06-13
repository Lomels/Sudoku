package it.lomele.sudoku.DATABASE;
import androidx.core.content.PermissionChecker;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;


@Entity
public class Score {
    @ColumnInfo(name = "time")
    public Time time;

    @PrimaryKey
    @ColumnInfo(name = "level")
    public String level;


    public Score(Time time, String level) {
        this.time = time;
        this.level = level;
    }
}
