package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import lombok.Data;

@Data public class CouponDataModel {
    @Expose @SerializedName("couponNo")
    private int no;
    @Expose @SerializedName(value = "subject", alternate = "title")
    private String name;
    @Expose @SerializedName(value = "couponValue", alternate = "price")
    private int value;
    @Expose @SerializedName(value = "couponConditionValue", alternate = "limitPrice")
    private int condition;
    @Expose @SerializedName("isExprired")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isExpired;
    @Expose @SerializedName("expireDate")
    private String expireDate;

    // -- Optional Data
    private boolean isAvailable;
}
