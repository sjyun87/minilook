package com.minilook.minilook.data.model.preorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class PreorderDataModel {
    @Expose @SerializedName("preorderNo")
    private int preorderNo;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("brandName")
    private String brandName;
    @Expose @SerializedName("image1")
    private String thumbUrl;
    @Expose @SerializedName("startDate")
    private long startDate;
    @Expose @SerializedName("endDate")
    private long endDate;
}
