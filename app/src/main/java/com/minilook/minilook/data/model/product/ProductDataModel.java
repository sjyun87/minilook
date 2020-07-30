package com.minilook.minilook.data.model.product;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.category.CategoryDataModel;

import java.util.List;
import lombok.Data;

@Data public class ProductDataModel {
    private int id;
    private String name;
    private String tag;
    private String desc;
    private String url_thumb;
    private String url_image;
    private List<String> images;
    private int price_origin;
    private int price_discount_percent;
    private int price;
    private boolean is_discount;
    private int point_percent;
    private int scrap_cnt;
    private boolean is_scrap;
    private int review_cnt;
    private int question_cnt;
    private String detail_info;
    private String detail_url;
    private CategoryDataModel category;
    private BrandDataModel brand;
    private List<ProductDataModel> related_products;
    private List<ProductColorDataModel> colors;
    private List<ProductSizeDataModel> sizes;
}
