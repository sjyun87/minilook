package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class ProductOptionDataModel {
    @Expose @SerializedName("colorName")
    private String color_name;
    @Expose @SerializedName("colorCode")
    private String color_code;
    @Expose @SerializedName("colorStocks")
    private int color_stock;
    @Expose @SerializedName("stocks")
    private List<ProductStockDataModel> stocks;
}