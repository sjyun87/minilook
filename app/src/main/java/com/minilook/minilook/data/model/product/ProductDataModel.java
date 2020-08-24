package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;

import java.util.List;
import lombok.Data;

@Data public class ProductDataModel {

    @Expose @SerializedName("productNo")
    private int id;
    @Expose @SerializedName("image")
    private String image_url;
    @Expose @SerializedName("brandName")
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
    @Expose @SerializedName("productPrice")
    private int price;
    @Expose @SerializedName("categoryName")
    private String category;
    @Expose @SerializedName("reviewCount")
    private int review_cnt;
    @Expose @SerializedName("scrapCount")
    private int scrap_cnt;



    //private int id;
    private String name;
    private String tag;
    private String desc;
    private String url_thumb;
    private String url_image;
    private List<String> images;
    private int price_origin;
    private int price_discount_percent;
    //private int price;
    //private boolean is_discount;
    private int point_percent;

    private boolean is_scrap;

    private int question_cnt;
    private String detail_info;
    private String detail_url;
    //private CategoryDataModel category;
    //private BrandDataModel brand;
    private List<ProductDataModel> related_products;
    private List<ProductColorDataModel> colors;
    private List<ProductSizeDataModel> sizes;
}
