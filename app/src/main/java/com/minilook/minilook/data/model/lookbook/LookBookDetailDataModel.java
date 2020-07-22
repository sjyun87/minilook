package com.minilook.minilook.data.model.lookbook;

import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookDetailDataModel {
    private String title;
    private String desc;
    private String tag;
    private String label;
    private List<String> image_urls;
    private List<ProductDataModel> products;
}
