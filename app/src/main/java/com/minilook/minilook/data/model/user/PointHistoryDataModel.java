package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class PointHistoryDataModel {
    @Expose @SerializedName("typeCode")
    private int type;
    @Expose @SerializedName("typeName")
    private String type_name;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("regDate")
    private String date_regist;
    @Expose @SerializedName("expireDate")
    private String date_expire;
}
