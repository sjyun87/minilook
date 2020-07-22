package com.minilook.minilook.data.model.product;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import lombok.Data;

@Data public class ProductDataModel {
    private int id;
    private String name;
    private String tag;
    private String desc;
    private String thumb_url;
    private int origin_price;
    private int discount_percent;
    private int price;
    private int isDiscount;
    private int scrap_cnt;
    private int review_cnt;
    private String category_name;



    private String notice;
    private String image_url;

    private boolean is_scrap;
    private String web_url;
    private BrandInfoDataModel brand;



    private String thumb;
}
