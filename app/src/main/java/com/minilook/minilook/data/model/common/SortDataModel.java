package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class SortDataModel {
    @Expose @SerializedName("code")
    private String code;
    @Expose @SerializedName("codeName")
    private String name;
}