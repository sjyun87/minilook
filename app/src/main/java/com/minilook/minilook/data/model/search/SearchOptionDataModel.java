package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class SearchOptionDataModel implements Serializable {
    @Expose @SerializedName("productOrderByCode")
    private String order;
    @Expose @SerializedName("memberNo")
    private int user_id;
    @Expose @SerializedName("brandNo")
    private int brand_id = 0;
    @Expose @SerializedName("genderCode")
    private String gender_code = null;
    @Expose @SerializedName("age")
    private int age = -1;
    @Expose @SerializedName("categoryName")
    private String category_name = null;
    @Expose @SerializedName("categoryCode")
    private String category_code = null;
    @Expose @SerializedName("categoryDetailCode")
    private String category_derail_code = null;
    @Expose @SerializedName("colorCode")
    private List<String> color_codes = null;
    @Expose @SerializedName("discount")
    private boolean isDiscount = false;
    @Expose @SerializedName("startPrice")
    private int price_min = -1;
    @Expose @SerializedName("endPrice")
    private int price_max = -1;
    @Expose @SerializedName("outOfStock")
    private boolean isStock = false;
    @Expose @SerializedName("sizeType")
    private int type = -1;
    @Expose @SerializedName("styleCode")
    private List<String> style_codes = null;
}
