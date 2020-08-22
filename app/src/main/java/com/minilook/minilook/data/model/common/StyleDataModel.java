package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class StyleDataModel {
    @Expose @SerializedName("styleCode")
    private String code;
    @Expose @SerializedName("styleName")
    private String name;
}
