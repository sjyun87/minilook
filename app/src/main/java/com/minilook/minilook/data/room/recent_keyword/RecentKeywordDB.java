package com.minilook.minilook.data.room.recent_keyword;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { RecentKeywordEntity.class }, version = 1, exportSchema = false)
public abstract class RecentKeywordDB extends RoomDatabase {
    public abstract RecentKeywordDAO getDAO();
}
