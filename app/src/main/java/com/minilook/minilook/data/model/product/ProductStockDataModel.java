package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.StockDeserializer;
import lombok.Data;

@Data public class ProductStockDataModel {
    @Expose @SerializedName("type")
    private String type;
    @Expose @SerializedName("code")
    private String code;
    @Expose @SerializedName("name")
    private String name;
    @Expose @SerializedName("stockQty")
    @JsonAdapter(StockDeserializer.class)
    private boolean isStock;
}
