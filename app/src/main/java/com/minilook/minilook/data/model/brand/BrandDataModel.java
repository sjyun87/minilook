package com.minilook.minilook.data.model.brand;

import java.util.List;
import lombok.Data;

@Data public class BrandDataModel {
    private int id;
    private String name;
    private String desc;
    private String tag;
    private String url_logo;
    private String url_thumb;
    private String url_image;
    private List<String> images;
    private boolean is_scrap;
    private int scrap_cnt;
}