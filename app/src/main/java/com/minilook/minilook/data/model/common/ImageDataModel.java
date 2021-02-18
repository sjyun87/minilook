package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class ImageDataModel implements Serializable {
    @Expose @SerializedName("reviewPhotoNo")
    private int itemNo;
    @Expose @SerializedName("sumb")
    private String thumbUrl;
    @Expose @SerializedName("origin")
    private String originUrl;
}
