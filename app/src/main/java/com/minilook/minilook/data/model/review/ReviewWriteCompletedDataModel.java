package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ReviewWriteCompletedDataModel {
    @Expose @SerializedName("isPhotoReview")
    private boolean isPhotoReview;
    @Expose @SerializedName("point")
    private int point;
}
