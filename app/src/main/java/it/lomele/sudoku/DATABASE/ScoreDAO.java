package it.lomele.sudoku.DATABASE;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.Time;
import java.util.List;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE level LIKE :level ")
    List<Score> getByLevel(String level);

    @Query("SELECT * FROM score WHERE time LIKE :time")
    List<Score> getByTime(String time);

    @Query("SELECT * FROM score WHERE id LIKE :id")
    Score getById(int id);


    @Insert
    public void insert(Score score);

    @Delete
    public void delete(Score score);
}
