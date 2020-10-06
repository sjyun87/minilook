package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class ReviewHistoryDataModel {
    @Expose @SerializedName("reviewCount")
    private int reviewCount;
    @Expose @SerializedName("reviews")
    private List<ReviewDataModel> reviews;
}
