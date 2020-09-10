package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import com.minilook.minilook.data.model.review.ReviewDataModel;
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
    @Expose @SerializedName("reviews")
    private List<ReviewDataModel> reviews;
    @Expose @SerializedName("scrapCount")
    private int scrap_cnt;
    @Expose @SerializedName("isScrap")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isScrap;
    @Expose @SerializedName("qaCount")
    private int question_cnt;
    @Expose @SerializedName("optionStocks")
    private List<ProductStockModel> productStocks;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("shippingType")
    private int shipping_type;
    @Expose @SerializedName("basicShippingFee")
    private int shipping_price;
    @Expose @SerializedName("conditinShippingFee")
    private int condition_shipping_price;
    @Expose @SerializedName("freeShippingCondition")
    private int condition_free_shipping;
    @Expose @SerializedName("relationProducts")
    private List<ProductDataModel> related_products;
    @Expose @SerializedName("recentNo")
    private int recent_id;
    @Expose @SerializedName("productStyleNo")
    private String info_style_no;
    @Expose @SerializedName("kcAuthInfo")
    private String info_kc_auth;
    @Expose @SerializedName("weight")
    private String info_weight;
    @Expose @SerializedName("color")
    private String info_color;
    @Expose @SerializedName("material")
    private String info_material;
    @Expose @SerializedName("age")
    private String info_age;
    @Expose @SerializedName("releaseDate")
    private String info_release_date;
    @Expose @SerializedName("manufacturer")
    private String info_manufacturer;
    @Expose @SerializedName("country")
    private String info_country;
    @Expose @SerializedName("caution")
    private String info_caution;
    @Expose @SerializedName("warranty")
    private String info_warranty;
    @Expose @SerializedName("damageCompensation")
    private String info_damage;
    @Expose @SerializedName("serviceCenter")
    private String info_service_center;
}
