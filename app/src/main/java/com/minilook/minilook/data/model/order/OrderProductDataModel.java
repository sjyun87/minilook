package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.io.Serializable;
import lombok.Data;

@Data public class OrderProductDataModel implements Serializable {
    @Expose @SerializedName("orderNo")
    private int orderOptionNo;
    @Expose @SerializedName("productNo")
    private int productNo;
    @Expose @SerializedName("productName")
    private String name;
    @Expose @SerializedName("image")
    private String thumbUrl;
    @Expose @SerializedName("optionNo")
    private int optionNo;
    @Expose @SerializedName("colorName")
    private String colorName;
    @Expose @SerializedName("colorCode")
    private String colorCode;
    @Expose @SerializedName("sizeName")
    private String sizeName;
    @Expose @SerializedName("sizeCode")
    private String sizeCode;
    @Expose @SerializedName("prodcutPrice")
    private int productPrice;
    @Expose @SerializedName("status")
    private int statusCode;
    @Expose @SerializedName("shippingUrl")
    private String trackingUrl;
    @Expose @SerializedName("infoTelephone")
    private String csPhone;
    @Expose @SerializedName("isReviewed")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isReviewed;

    private String brandName;
}
