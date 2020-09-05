package com.minilook.minilook.data.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class VersionDataModel {
    @Expose @SerializedName("appName")
    private String name;
    @Expose @SerializedName("appStatus")
    private int status;
}
