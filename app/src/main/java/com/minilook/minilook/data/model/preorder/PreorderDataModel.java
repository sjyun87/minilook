package com.minilook.minilook.data.model.preorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class PreorderDataModel {
    @Expose @SerializedName("preorderNo")
    private int preorderNo;
    @Expose @SerializedName("status")
    private int status;
    @Expose @SerializedName("statusName")
    private String statusName;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("description")
    private String desc;
    @Expose @SerializedName("image1")
    private String thumbUrl;
    @Expose @SerializedName("brandName")
    private String brandName;
    @Expose @SerializedName("images")
    private List<String> images;
    @Expose @SerializedName("startDate")
    private long startDate;
    @Expose @SerializedName("endDate")
    private long endDate;
    @Expose @SerializedName("deliveryDate")
    private long deliveryDate;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
