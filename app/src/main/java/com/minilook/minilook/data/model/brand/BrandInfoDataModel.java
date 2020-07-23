package com.minilook.minilook.data.model.brand;

import lombok.Data;

@Data public class BrandInfoDataModel {
    private int id;
    private String name;
    private String desc;
    private String tag;
    private String url_image;
    private String url_logo;


    private String url_homepage;
    private int scrap_count;
    private boolean is_scrap;
}