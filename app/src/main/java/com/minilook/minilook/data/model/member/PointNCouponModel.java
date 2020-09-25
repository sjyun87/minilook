package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class PointNCouponModel {
    @Expose @SerializedName("couponCount")
    private int coupon;
    @Expose @SerializedName("point")
    private int point;
}
