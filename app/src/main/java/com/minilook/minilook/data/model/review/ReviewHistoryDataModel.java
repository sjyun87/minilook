package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.image.ImageDataModel;
import java.util.List;
import lombok.Data;

@Data public class ReviewHistoryDataModel {
    @Expose @SerializedName(value = "reviewCount", alternate = "totalCount")
    private int reviewCount;
    @Expose @SerializedName("reviews")
    private List<ReviewDataModel> reviews;
    @Expose @SerializedName("reviewStatistics")
    private ReviewRatingDataModel rating;
    @Expose @SerializedName("totalPhotos")
    private List<ImageDataModel> photos;
}
