package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class ReviewRatingDataModel {
    @Expose @SerializedName("reviewSatisfactionValue")
    private String satisfaction;
    @Expose @SerializedName("reviewMaxSizeValue")
    private RatingDataModel sizeRating;
    @Expose @SerializedName("reviewSizeList")
    private List<RatingDataModel> sizeRatingDetail;
}
