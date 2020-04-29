package com.solarexsoft.solarexroomwordsample

import androidx.room.*

/**
 * Created by Solarex on 2020/4/29/3:53 PM
 * Desc:
 */

@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)

@Dao
interface WordDao {
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}