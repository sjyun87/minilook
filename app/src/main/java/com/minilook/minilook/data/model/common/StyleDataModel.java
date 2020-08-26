package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class StyleDataModel {
    @Expose @SerializedName(value = "styleCode", alternate = "code")
    private String code;
    @Expose @SerializedName(value = "styleName", alternate = "code_name")
    private String name;

    // -- Controller
    private int position;
    private boolean isSelected;
}
