package com.solarexsoft.solarexroomwordsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Solarex on 2020/4/29/3:53 PM
 * Desc:
 */

@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)