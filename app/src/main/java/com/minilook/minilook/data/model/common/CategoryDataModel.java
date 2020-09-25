package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class CategoryDataModel {
    @Expose @SerializedName("catecode")
    private String code;
    @Expose @SerializedName("catename")
    private String name;
    @Expose @SerializedName("icon")
    private String iconUrl;

    // -- Controller
    private int position;
    private boolean isSelected;
}
