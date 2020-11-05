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
    private int productNo;
    @Expose @SerializedName("productName")
    private String productName;
    @Expose @SerializedName("lookbookProductDescription")
    private String productDesc;
    @Expose @SerializedName("categoryName")
    private String category;
    @Expose @SerializedName("image")
    private String imageUrl;
    @Expose @SerializedName("images")
    private List<String> images;
    @Expose @SerializedName("brandNo")
    private int brandNo;
    @Expose @SerializedName(value = "brandName", alternate = "brandNm")
    private String brandName;
    @Expose @SerializedName("brandLogo")
    private String brandLogo;
    @Expose @SerializedName("isdiscount")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isDiscount;
    @Expose @SerializedName("discountPercent")
    private int discountPercent;
    @Expose @SerializedName(value = "productPrice", alternate = "price")
    private int price;
    @Expose @SerializedName("supplyPrice")
    private int priceOrigin;
    @Expose @SerializedName("stockName")
    private String displayLabel;
    @Expose @SerializedName("stockCode")
    private int displayCode;
    @Expose @SerializedName("detailUrl")
    private String detailUrl;
    @Expose @SerializedName("reviewCount")
    private int reviewCount;
    @Expose @SerializedName("reviews")
    private List<ReviewDataModel> reviews;
    @Expose @SerializedName("scrapCount")
    private int scrapCount;
    @Expose @SerializedName("isScrap")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isScrap;
    @Expose @SerializedName("qaCount")
    private int questionCount;
    @Expose @SerializedName("optionStocks")
    private List<ProductStockDataModel> stocks;
    @Expose @SerializedName("point")
    private int point;
    @Expose @SerializedName("shippingType")
    private int shippingType;
    @Expose @SerializedName("basicShippingFee")
    private int shippingPrice;
    @Expose @SerializedName("conditinShippingFee")
    private int conditionShippingPrice;
    @Expose @SerializedName("freeShippingCondition")
    private int conditionFreeShipping;
    @Expose @SerializedName("relationProducts")
    private List<ProductDataModel> relatedProducts;
    @Expose @SerializedName("recentNo")
    private int recentNo;
    @Expose @SerializedName("productStyleNo")
    private String infoStyleNo;
    @Expose @SerializedName("kcAuthInfo")
    private String infoKCAuth;
    @Expose @SerializedName("weight")
    private String infoWeight;
    @Expose @SerializedName("color")
    private String infoColor;
    @Expose @SerializedName("material")
    private String infoMaterial;
    @Expose @SerializedName("age")
    private String infoAge;
    @Expose @SerializedName("releaseDate")
    private String infoReleaseDate;
    @Expose @SerializedName("manufacturer")
    private String infoManufacturer;
    @Expose @SerializedName("country")
    private String infoCountry;
    @Expose @SerializedName("caution")
    private String infoCaution;
    @Expose @SerializedName("warranty")
    private String infoWarranty;
    @Expose @SerializedName("damageCompensation")
    private String infoDamage;
    @Expose @SerializedName("serviceCenter")
    private String infoServiceCenter;
    @Expose @SerializedName("ispreorder")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isPreorder;
    @Expose @SerializedName("preorderNo")
    private int preorderNo;
    @Expose @SerializedName("type")
    private String type;
    @Expose @SerializedName("tagName")
    private String tag;
}
