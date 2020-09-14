package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.data.model.user.PointDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import java.util.List;
import lombok.Data;

@Data public class OrderSheetDataModel {
    @Expose @SerializedName("address")
    private ShippingDataModel shipping;
    @Expose @SerializedName("coupon")
    private List<CouponDataModel> coupons;
    @Expose @SerializedName("point")
    private PointDataModel point;
    @Expose @SerializedName("member")
    private UserDataModel user;
}