package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.util.List;
import lombok.Data;

@Data public class ProductDataModel {
    @Expose @SerializedName("productNo")
    private int product_id;
    @Expose @SerializedName("image")
    private String image_url;
    @Expose @SerializedName("images")
    private List<String> product_images;
    @Expose @SerializedName("brandNo")
    private int brand_id;
    @Expose @SerializedName(value = "brandName", alternate = "brandNm")
    private String brand_name;
    @Expose @SerializedName("brandLogo")
    private String brand_logo;
    @Expose @SerializedName("productName")
    private String product_name;
    @Expose @SerializedName("lookbookProductDescription")
    private String product_desc;
    @Expose @SerializedName("isdiscount")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isDiscount;
    @Expose @SerializedName("discountPercent")
    private int discount_percent;
    @Expose @SerializedName(value = "productPrice", alternate = "price")
    private int price;
    @Expose @SerializedName("supplyPrice")
    private int price_origin;
    @Expose @SerializedName("stockName")
    private String display_label;
    @Expose @SerializedName("stockCode")
    private int display_code;
    @Expose @SerializedName("detailUrl")
    private String detail_url;
    @Expose @SerializedName("categoryName")
    private String category;
    @Expose @SerializedName("reviewCount")
    private int review_cnt;
    @Expose @SerializedName("scrapCount")
    private int scrap_cnt;
    @Expose @SerializedName("isScrap")
    private boolean isScrap;
    @Expose @SerializedName("qaCount")
    private int question_cnt;
    @Expose @SerializedName("optionStocks")
    private List<ProductStockModel> productStocks;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("freeShipping")
    private int price_shipping_conditional;
    @Expose @SerializedName("shippingFee")
    private int price_shipping;
    @Expose @SerializedName("relationProducts")
    private List<ProductDataModel> related_products;
}
