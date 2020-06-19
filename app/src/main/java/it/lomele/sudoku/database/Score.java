package it.lomele.sudoku.database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity
public class Score {
    @ColumnInfo(name = "time")
    private String time;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "board")
    private List<Integer> board;

    @ColumnInfo(name = "result")
    private int result;


    public Score(String time, int level, List<Integer> board, int result) {
        this.time = time;
        this.level = level;
        this.board = board;
        this.result = result;
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

    public int getResult() {
        return result;
    }

}
