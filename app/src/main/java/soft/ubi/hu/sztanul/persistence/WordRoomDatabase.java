package soft.ubi.hu.sztanul.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import soft.ubi.hu.sztanul.persistence.dao.WordDao;
import soft.ubi.hu.sztanul.persistence.entity.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase instance;

    public static synchronized WordRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WordRoomDatabase.class, "word_database")
                    .build();
        }
        return instance;
    }

    public abstract WordDao wordDao();
}