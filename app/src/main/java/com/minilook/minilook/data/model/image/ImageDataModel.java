package com.minilook.minilook.data.model.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ImageDataModel {
    @Expose @SerializedName("sumb")
    private String thumbUrl;
    @Expose @SerializedName("origin")
    private String originUrl;
}
