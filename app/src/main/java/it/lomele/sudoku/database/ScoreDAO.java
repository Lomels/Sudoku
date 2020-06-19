package it.lomele.sudoku.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query ("SELECT COUNT(result) FROM score WHERE result = 1")
    int countWin();

    @Query("SELECT COUNT(*) FROM score")
    int size();


    @Insert
    public void insert(Score score);

    @Delete
    public void delete(Score score);
}
