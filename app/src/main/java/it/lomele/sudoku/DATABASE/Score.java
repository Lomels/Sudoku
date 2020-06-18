package it.lomele.sudoku.DATABASE;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity
public class Score {
    @ColumnInfo(name = "time")
    public String time;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "board")
    public List<Integer> board;


    public Score(String time, int level, List<Integer> board) {
        this.time = time;
        this.level = level;
        this.board = board;
    }

    @Override
    public String toString(){
        String mString = "Level: "+level+", Time: "+time;
        return mString;
    }

    public int getLevel() {
        return this.level;
    }

    public String getTime() {
        return this.time;
    }

    public List<Integer> getBoard(){ return this.board;}
}
