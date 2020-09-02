package com.minilook.minilook.data.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import java.util.List;
import lombok.Data;

@Data public class BrandDataModel {
    @Expose @SerializedName("brandNo")
    private int id;
    @Expose @SerializedName("name")
    private String brand_name;
    @Expose @SerializedName("logo")
    private String brand_logo;
    @Expose @SerializedName("description")
    private String brand_desc;
    @Expose @SerializedName("styleTags")
    private String brand_tag;
    @Expose @SerializedName("image")
    private String image_url;
    @Expose @SerializedName("scrapCount")
    private int scrap_cnt;
    @Expose @SerializedName("isScrap")
    private boolean isScrap;
    @Expose @SerializedName("styles")
    private List<StyleDataModel> styles;
    @Expose @SerializedName("styleImages")
    private List<String> style_images;
    @Expose @SerializedName("productOrderbyCodes")
    private List<SortDataModel> sorts;

    // -- Controller
    private int position;
    private boolean isSelect;
}