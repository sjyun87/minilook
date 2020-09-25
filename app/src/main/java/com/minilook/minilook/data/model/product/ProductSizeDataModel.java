package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class ProductSizeDataModel {
    @Expose @SerializedName("optionNo")
    private int optionNo;
    @Expose @SerializedName("sizeName")
    private String sizeName;
    @Expose @SerializedName("sizeCode")
    private int sizeCode;
    @Expose @SerializedName("stockQty")
    private int sizeStock;
    @Expose @SerializedName("extraAmount")
    private int priceAdd;
    @Expose @SerializedName("maxOrderQuantity")
    private int orderLimit;
}