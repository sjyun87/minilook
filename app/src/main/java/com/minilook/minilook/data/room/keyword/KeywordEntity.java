package com.minilook.minilook.data.room.keyword;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;

@Entity(tableName = "T_KEYWORD")
@Data public class KeywordEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String keyword;
}
