package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class SearchOptionDataModel implements Serializable {
    @Expose @SerializedName("productOrderByCode")
    private String sort_code;
    @Expose @SerializedName("brandNo")
    private int brand_no;
    @Expose @SerializedName("genderCode")
    private String gender_code;
    @Expose @SerializedName("age")
    private int age;
    @Expose @SerializedName("categoryName")
    private String category_name;
    @Expose @SerializedName("categoryCode")
    private String category_code;
    @Expose @SerializedName("categoryDetailCode")
    private String category_derail_code;
    @Expose @SerializedName("colorCode")
    private List<String> color_codes;
    @Expose @SerializedName("discount")
    private boolean isDiscount;
    @Expose @SerializedName("startPrice")
    private int price_min;
    @Expose @SerializedName("endPrice")
    private int price_max;
    @Expose @SerializedName("outOfStock")
    private boolean isStock;
    @Expose @SerializedName("sizeType")
    private int type;
    @Expose @SerializedName("styleCode")
    private List<String> style_codes;
}
