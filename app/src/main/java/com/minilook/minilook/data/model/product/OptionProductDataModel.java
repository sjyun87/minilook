package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.util.List;
import lombok.Data;

@Data public class OptionProductDataModel {
    @Expose @SerializedName("productNo")
    private int productNo;
    @Expose @SerializedName("productName")
    private String productName;
    @Expose @SerializedName("image")
    private String image;
    @Expose @SerializedName("isdiscount")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isDiscount;
    @Expose @SerializedName("discountPercent")
    private int discountPercent;
    @Expose @SerializedName("productPrice")
    private int price;
    @Expose @SerializedName("stockName")
    private String displayLabel;
    @Expose @SerializedName("stockCode")
    private int displayCode;
    @Expose @SerializedName("options")
    private List<OptionColorDataModel> colors;

    private int position;
    private boolean isBonus;
}