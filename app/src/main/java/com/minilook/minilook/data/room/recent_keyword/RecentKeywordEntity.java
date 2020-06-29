package com.minilook.minilook.data.room.recent_keyword;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity(tableName = "T_RECENT_KEYWORD")
@Data public class RecentKeywordEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String keyword;
}
