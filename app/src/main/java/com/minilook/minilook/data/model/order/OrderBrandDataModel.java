package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class OrderBrandDataModel {
    @Expose @SerializedName("brandNo")
    private int brandNo;
    @Expose @SerializedName("logo")
    private String brandLogo;
    @Expose @SerializedName("brandName")
    private String brandName;
    @Expose @SerializedName("shippingPrice")
    private int shippingPrice;
    @Expose @SerializedName("options")
    private List<OrderGoodsDataModel> goods;
}
