package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class KeywordDataModel {
    @Expose @SerializedName("keyword")
    private String keyword;
}
