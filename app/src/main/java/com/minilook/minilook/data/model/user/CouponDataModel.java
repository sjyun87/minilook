package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import lombok.Data;

@Data public class CouponDataModel {
    @Expose @SerializedName("subject")
    private String name;
    @Expose @SerializedName("couponValue")
    private int coupon;
    @Expose @SerializedName("couponConditionValue")
    private int use_condition;
    @Expose @SerializedName("isExprired")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isEnd;
    @Expose @SerializedName("expireDate")
    private String date_end;
}
