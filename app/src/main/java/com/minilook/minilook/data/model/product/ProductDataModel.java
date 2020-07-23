package com.minilook.minilook.data.model.product;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.category.CategoryDataModel;

import lombok.Data;

@Data public class ProductDataModel {
    private int id;
    private String name;
    private String tag;
    private String desc;
    private String url_thumb;
    private String url_image;
    private int price_origin;
    private int price_discount_percent;
    private int price;
    private boolean is_discount;
    private int scrap_cnt;
    private boolean is_scrap;
    private int review_cnt;
    private CategoryDataModel category;
    private BrandInfoDataModel brand;
}
