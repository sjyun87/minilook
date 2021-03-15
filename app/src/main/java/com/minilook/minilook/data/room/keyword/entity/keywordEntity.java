package com.minilook.minilook.data.room.keyword.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "T_KEYWORD")
public class keywordEntity {
    @PrimaryKey
    private int id;
    private String keyword;
}
