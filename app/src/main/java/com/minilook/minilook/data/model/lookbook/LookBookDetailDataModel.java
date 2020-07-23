package com.minilook.minilook.data.model.lookbook;

import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class LookBookDetailDataModel {
    private String label;
    private String title;
    private String desc;
    private String tag;
    private List<String> images;
    private List<ProductDataModel> products;
}
