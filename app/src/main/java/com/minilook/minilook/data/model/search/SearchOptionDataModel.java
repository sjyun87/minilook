package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class SearchOptionDataModel implements Serializable {
    @Expose @SerializedName("productOrderByCode")
    private String sortCode;
    @Expose @SerializedName("brandNo")
    private int brandNo;
    @Expose @SerializedName("genderCode")
    private String genderCode;
    @Expose @SerializedName("age")
    private int age;
    @Expose @SerializedName("categoryName")
    private String categoryName;
    @Expose @SerializedName("categoryCode")
    private String categoryCode;
    @Expose @SerializedName("categoryDetailCode")
    private String categoryDerailCode;
    @Expose @SerializedName("colorCode")
    private List<String> colorCodes;
    @Expose @SerializedName("discount")
    private boolean isDiscount;
    @Expose @SerializedName("startPrice")
    private int minPrice;
    @Expose @SerializedName("endPrice")
    private int maxPrice;
    @Expose @SerializedName("outOfStock")
    private boolean isStock;
    @Expose @SerializedName("sizeType")
    private int type;
    @Expose @SerializedName("styleCode")
    private List<String> styleCodes;

    private String keyword;
    private boolean isFilerSearch;
}
