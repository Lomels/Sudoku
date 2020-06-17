package it.lomele.sudoku.DATABASE;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class},version = 4, exportSchema = false)
public abstract class Db extends RoomDatabase {

    private static final String DB_NAME = "score_db";
    private static Db instance;

    public static synchronized Db getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Db.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;

    }

    public abstract ScoreDAO scoreDAO();
}
