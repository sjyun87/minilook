package com.minilook.minilook.data.room.keyword;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface KeywordDAO {
    @Query("SELECT keyword FROM T_KEYWORD ORDER BY id DESC")
    List<String> getRecentKeywords();

    @Query("SELECT COUNT(keyword) FROM T_KEYWORD WHERE keyword = :keyword")
    int hasKeyword(String keyword);

    @Query("INSERT INTO T_KEYWORD(keyword) VALUES (:keyword)")
    void insertKeyword(String keyword);

    @Query("DELETE FROM T_KEYWORD WHERE keyword = :keyword")
    void deleteKeyword(String keyword);

    @Query("DELETE FROM T_KEYWORD WHERE keyword = "
        + "(SELECT keyword FROM T_KEYWORD WHERE id = (SELECT MIN(id) FROM T_KEYWORD))")
    void deleteOldKeyword();

    @Query("DELETE FROM T_KEYWORD")
    void clearKeyword();
}
