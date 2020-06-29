package com.minilook.minilook.data.model.lookbook;

import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookModuleDataModel {
    private int id;
    private int module_type;
    private String title;
    private String desc;
    private String bg_url;
    private List<LookBookDetailImageDataModel> bg_detail_images;
    private List<ProductDataModel> products;
}
