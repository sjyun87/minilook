package com.minilook.minilook.data.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class CategoryDataModel {
    @Expose @SerializedName("cateNo")
    private int id;
    @Expose @SerializedName("catename")
    private String name;
    @Expose @SerializedName("icon")
    private String image_url;
}
