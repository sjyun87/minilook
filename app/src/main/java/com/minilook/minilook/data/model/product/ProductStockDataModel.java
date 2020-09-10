package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ProductStockDataModel {
    @Expose @SerializedName("optionNo")
    private int option_id;
    @Expose @SerializedName("sizeName")
    private String size_name;
    @Expose @SerializedName("sizeCode")
    private int size_code;
    @Expose @SerializedName("stockQty")
    private int size_stock;
    @Expose @SerializedName("extraAmount")
    private int price_add;
    @Expose @SerializedName("maxOrderQuantity")
    private int order_limit;
}