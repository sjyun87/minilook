package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class KeyDataModel {
    @Expose @SerializedName("accssky")
    private String accessKey;
    @Expose @SerializedName("scrtky")
    private String secretKey;
}
