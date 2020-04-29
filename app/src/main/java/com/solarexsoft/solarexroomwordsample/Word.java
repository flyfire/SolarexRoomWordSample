package com.solarexsoft.solarexroomwordsample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Solarex on 2020/4/29/5:45 PM
 * Desc:
 */
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}
}
