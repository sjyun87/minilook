package com.minilook.minilook.data.model.promotion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class PromotionDataModel {
    @Expose @SerializedName("promotionNo")
    private int promotion_id;
    @Expose @SerializedName("image")
    private String image_url;
    @Expose @SerializedName("sumNameUrl")
    private String thumb_url;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("url")
    private String event_url;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
