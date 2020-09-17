package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class OrderDataModel {
    @Expose @SerializedName("mid")
    private String orderNo;
    @Expose @SerializedName("registDateName")
    private String orderDate;
    @Expose @SerializedName("receiptId")
    private String receiptId;
    @Expose @SerializedName("payMethodName")
    private String paymentMethod;
    @Expose @SerializedName("productPrice")
    private int productPrice;
    @Expose @SerializedName("shippingPrice")
    private int shippingPrice;
    @Expose @SerializedName("couponPrice")
    private int coupon;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("paymentPrice")
    private int paymentPrice;
    @Expose @SerializedName("brands")
    private List<OrderBrandDataModel> brands;
}
