package it.lomele.sudoku.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import it.lomele.sudoku.utils.Converters;

@Database(entities = {Score.class}, version = 7, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ScoreDatabase extends RoomDatabase {

    private static final String DB_NAME = "score_db";
    private static ScoreDatabase instance;

    public static synchronized ScoreDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ScoreDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;

    }

    public abstract ScoreDAO scoreDAO();
}
