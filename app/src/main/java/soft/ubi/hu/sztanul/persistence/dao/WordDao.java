package soft.ubi.hu.sztanul.persistence.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import soft.ubi.hu.sztanul.persistence.entity.Word;

@Dao
public interface WordDao {

    @Query("select * from word")
    LiveData<List<Word>> findAllWord();

    @Insert
    void insert(Word... words);

    @Query("delete from word")
    void deleteAll();

    @Delete
    void delete(Word word);
}
