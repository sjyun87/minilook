package com.minilook.minilook.data.room.recent_keyword;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecentKeywordDAO {
    @Query("SELECT keyword FROM T_RECENT_KEYWORD ORDER BY id DESC")
    List<String> getRecentKeywordList();

    @Query("SELECT COUNT(keyword) FROM T_RECENT_KEYWORD WHERE keyword = :keyword")
    int hasKeyword(String keyword);

    @Query("INSERT INTO T_RECENT_KEYWORD(keyword) VALUES (:keyword)")
    void insertKeyword(String keyword);

    @Query("DELETE FROM T_RECENT_KEYWORD WHERE keyword = :keyword")
    void deleteKeyword(String keyword);

    @Query("DELETE FROM T_RECENT_KEYWORD WHERE keyword = "
        + "(SELECT keyword FROM T_RECENT_KEYWORD WHERE id = (SELECT MIN(id) FROM T_RECENT_KEYWORD))")
    void deleteOldKeyword();

    @Query("DELETE FROM T_RECENT_KEYWORD")
    void deleteAllKeyword();
}
