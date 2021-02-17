package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class RatingDataModel {
    @Expose @SerializedName("reviewSizeCode")
    private String code;
    @Expose @SerializedName("reviewSizeValue")
    private int value;
}
