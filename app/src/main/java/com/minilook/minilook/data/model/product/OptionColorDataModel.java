package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class OptionColorDataModel {
    @Expose @SerializedName("colorName")
    private String colorName;
    @Expose @SerializedName("colorCode")
    private String colorCode;
    @Expose @SerializedName("colorStocks")
    private int colorStock;
    @Expose @SerializedName("stocks")
    private List<OptionSizeDataModel> sizes;
}