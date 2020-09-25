package com.minilook.minilook.data.model.promotion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class PromotionDataModel {
    @Expose @SerializedName("promotionNo")
    private int promotionNo;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("image")
    private String imageUrl;
    @Expose @SerializedName("sumNameUrl")
    private String thumbUrl;
    @Expose @SerializedName("url")
    private String eventUrl;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
