package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import com.minilook.minilook.data.model.image.ImageDataModel;
import java.util.List;
import lombok.Data;

@Data public class ReviewDataModel {
    @Expose @SerializedName("reviewNo")
    private int reviewNo;
    @Expose @SerializedName("nickname")
    private String nickname;
    @Expose @SerializedName("orderDate")
    private String orderDate;
    @Expose @SerializedName("regDate")
    private String registDate;
    @Expose @SerializedName("productNo")
    private int productNo;
    @Expose @SerializedName("brandName")
    private String brandName;
    @Expose @SerializedName("productName")
    private String productName;
    @Expose @SerializedName("productImage")
    private String imageUrl;
    @Expose @SerializedName("colorName")
    private String colorName;
    @Expose @SerializedName("colorCode")
    private String colorCode;
    @Expose @SerializedName("sizeName")
    private String sizeName;
    @Expose @SerializedName("sizeCode")
    private String sizeCode;
    @Expose @SerializedName("reviewSexCode")
    private String genderCode;
    @Expose @SerializedName("age")
    private int age;
    @Expose @SerializedName("height")
    private int height;
    @Expose @SerializedName("weight")
    private int weight;
    @Expose @SerializedName("reviewSatisfactionCode")
    private String satisfactionCode;
    @Expose @SerializedName("reviewSizeCode")
    private String sizeRatingCode;
    @Expose @SerializedName("photos")
    List<ImageDataModel> photos;
    @Expose @SerializedName("content")
    private String review;
    @Expose @SerializedName("isHelp")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isHelp;
    @Expose @SerializedName("helpCount")
    private int helpCount;
}
