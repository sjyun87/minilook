package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class CodeDataModel {
    @Expose @SerializedName(value = "code", alternate = { "styleCode", "catecode" })
    private String code;
    @Expose @SerializedName(value = "codeName", alternate = { "code_name", "styleName", "catename" })
    private String name;
    @Expose @SerializedName("icon")
    private String iconUrl;

    // -- Optional Data
    private boolean isSelected;



    private int position;
}
