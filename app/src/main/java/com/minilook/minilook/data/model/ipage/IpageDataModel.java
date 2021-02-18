package com.minilook.minilook.data.model.ipage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class IpageDataModel {
    @Expose @SerializedName("memberNo")
    private int user_id;
    @Expose @SerializedName("nickname")
    private String nick;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("couponCount")
    private int coupon;
    @Expose @SerializedName("orderCount")
    private int order_complete;
    @Expose @SerializedName("readyCount")
    private int packing;
    @Expose @SerializedName("shippingCount")
    private int delivery;
    @Expose @SerializedName("completeCount")
    private int delivery_complete;
    @Expose @SerializedName("cartCount")
    private int shoppingbagCount;
    @Expose @SerializedName("reviewCount")
    private int reviewCount;
    @Expose @SerializedName("noReplyInquiryCount")
    private int questionCount;
}
