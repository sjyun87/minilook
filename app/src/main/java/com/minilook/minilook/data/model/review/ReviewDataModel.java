package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import lombok.Data;

@Data public class ReviewDataModel {
    //@Expose @SerializedName("")
    private String user_name;

    @Expose @SerializedName("memo")
    private String contents;
    @Expose @SerializedName("isHelp")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isHelp;
    @Expose @SerializedName("helpCount")
    private int help_cnt;


    //@Expose @SerializedName("")
    private String regist_date;
}
