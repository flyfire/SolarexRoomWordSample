package com.solarexsoft.solarexroomwordsample

import androidx.lifecycle.LiveData

/**
 * Created by Solarex on 2020/4/29/4:20 PM
 * Desc:
 */
class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}