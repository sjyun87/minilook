package com.minilook.minilook.data.model.brand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
    @Expose @SerializedName("image")
    private String image_url;
    @Expose @SerializedName("scrapCount")
    private int scrap_cnt;
    @Expose @SerializedName("styles")
    private List<StyleDataModel> styles;
    @Expose @SerializedName("styleImages")
    private List<String> style_images;

    // -- Controller
    private int position;
    private boolean isSelect;







    //private String name;
    private String desc;
    private String tag;
    private String url_logo;
    private String url_thumb;
    private String url_image;
    private List<String> images;
    private boolean is_scrap;

}