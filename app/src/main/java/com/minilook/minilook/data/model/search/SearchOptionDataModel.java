package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class SearchOptionDataModel {
    @Expose @SerializedName("current")
    private int page;
    @Expose @SerializedName("brandNo")
    private int brand_id;
    @Expose @SerializedName("genderCode")
    private String gender_code;
    @Expose @SerializedName("age")
    private int age;
    @Expose @SerializedName("categoryCode")
    private String category_code;
    @Expose @SerializedName("categoryDetailCode")
    private String category_derail_code;
    @Expose @SerializedName("colorCode")
    private List<String> color_codes;
    @Expose @SerializedName("discount")
    private boolean isDiscount;
    @Expose @SerializedName("endPrice")
    private int price_end;


}
