package com.minilook.minilook.data.model.product;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;

import lombok.Data;

@Data public class ProductDataModel {
    private int id;
    private String name;
    private String tag;
    private String desc;
    private String notice;
    private String image_url;
    private String image_thumb_url;
    private int price_origin;
    private int price_sale_percent;
    private int price_sale;
    private boolean is_sale;
    private int scrap_count;
    private boolean is_scrap;
    private String web_url;
    private BrandInfoDataModel brand;

}
