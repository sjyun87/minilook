package com.minilook.minilook.data.room.keyword;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { KeywordEntity.class }, version = 1, exportSchema = false)
public abstract class KeywordDB extends RoomDatabase {
    public abstract KeywordDAO getDAO();
}
