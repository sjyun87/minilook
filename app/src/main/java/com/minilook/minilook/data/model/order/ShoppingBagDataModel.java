package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ShoppingProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class ShoppingBagDataModel {
    @Expose @SerializedName("brandNo")
    private int brand_id;
    @Expose @SerializedName("brandName")
    private String brand_name;
    @Expose @SerializedName("logo")
    private String brand_logo;
    @Expose @SerializedName("shippingMehtod")
    private int shipping_type;
    @Expose @SerializedName("shippingMehtodName")
    private String shipping_type_name;
    @Expose @SerializedName("shippingFee")
    private int shipping_price;
    @Expose @SerializedName("shippingLimit")
    private int shipping_limit;
    @Expose @SerializedName("products")
    private List<ShoppingProductDataModel> products;

    // -- Control Data
    private boolean isShippingFree;
    private int productsPrice;
    private int remainShippingFree;
}
