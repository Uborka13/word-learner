package soft.ubi.hu.sztanul.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import soft.ubi.hu.sztanul.AppExecutors;
import soft.ubi.hu.sztanul.persistence.WordRoomDatabase;
import soft.ubi.hu.sztanul.persistence.dao.WordDao;
import soft.ubi.hu.sztanul.persistence.entity.Word;

public class WordViewModel extends AndroidViewModel {

    private WordDao wordDao;
    private AppExecutors appExecutors;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordDao = WordRoomDatabase.getDatabase(application).wordDao();
        appExecutors = AppExecutors.getInstance();
    }

    public LiveData<List<Word>> getAllWords() {
        return wordDao.findAllWord();
    }

    public void saveWord(Word word) {
        appExecutors.diskIO().execute(() -> wordDao.insert(word));
    }

    public void deleteWord(Word word){
        appExecutors.diskIO().execute(() -> wordDao.delete(word));
    }
}
