package com.solarexsoft.solarexroomwordsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Solarex on 2020/4/29/5:54 PM
 * Desc:
 */
public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getALlWords() {
        return mAllWords;
    }

    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
