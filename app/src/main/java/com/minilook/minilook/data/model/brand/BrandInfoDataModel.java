package com.minilook.minilook.data.model.brand;

import lombok.Data;

@Data public class BrandInfoDataModel {
    private int id;
    private String name;
    private String desc;
    private String tag;
    private String image_url;
    private String logo_url;
    private String homepage_url;
    private int scrap_count;
    private boolean is_scrap;
}