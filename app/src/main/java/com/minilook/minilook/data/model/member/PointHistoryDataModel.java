package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class PointHistoryDataModel {
    @Expose @SerializedName("typeCode")
    private int code;
    @Expose @SerializedName("typeName")
    private String type;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("regDate")
    private String registDate;
    @Expose @SerializedName("expireDate")
    private String expireDate;
}
