package com.solarexsoft.solarexroomwordsample

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Solarex on 2020/4/29/3:53 PM
 * Desc:
 */

// https://developer.android.com/reference/android/arch/persistence/room/Entity
// If a field is transient, it is automatically ignored unless it is annotated with ColumnInfo, Embedded or Relation.
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey @ColumnInfo(name = "word") var word: String = "",
    @Transient @ColumnInfo(name = "solarex_test_meaning") var meaning: String = "",
    @Ignore val pronounce: String
) {
    constructor() :this("", "", ""){}
}

@Dao
interface WordDao {
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}

@Database(entities = arrayOf(Word::class), version = 2, exportSchema = true)
public abstract class WordRoomDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstanceDoubleCheck = INSTANCE
                if (tempInstanceDoubleCheck != null) {
                    return tempInstanceDoubleCheck
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .addMigrations(object : Migration(1, 2){
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("alter table word_table add column solarex_test_meaning text not null default ''")
                        }

                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                wordRoomDatabase ->
                scope.launch {
                    populateDatabase(wordRoomDatabase.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()
            var word = Word("hello", "meaning0", "pronounce0")
            wordDao.insert(word)
            word = Word("world", "meaning1", "pronounce1")
            wordDao.insert(word)
        }
    }
}

