package com.minilook.minilook.data.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class BrandDataModel {
    @Expose @SerializedName("brandNo")
    private int brandNo;
    @Expose @SerializedName(value = "name", alternate = "brandName")
    private String brandName;
    @Expose @SerializedName("logo")
    private String brandLogo;
    @Expose @SerializedName("description")
    private String brandDesc;
    @Expose @SerializedName("styleTags")
    private String brandTag;
    @Expose @SerializedName("image")
    private String imageUrl;
    @Expose @SerializedName("scrapCount")
    private int scrapCount;
    @Expose @SerializedName("isScrap")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isScrap;
    @Expose @SerializedName("styles")
    private List<CodeDataModel> styles;
    @Expose @SerializedName("styleImages")
    private List<String> styleImages;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}