package com.minilook.minilook.data.model.lookbook;

import com.minilook.minilook.data.model.image.ImageDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookDataModel {
    private int id;
    private int type;
    private String label;
    private String title;
    private String desc;
    private String tag;
    private String product_info;
    private String url_bg;
    private List<ImageDataModel> styles;
    private List<ProductDataModel> products;
}
