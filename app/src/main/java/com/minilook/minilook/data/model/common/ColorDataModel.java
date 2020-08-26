package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ColorDataModel {
    @Expose @SerializedName("code")
    private String code;
    @Expose @SerializedName("code_name")
    private String name;
}
