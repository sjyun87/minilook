package com.minilook.minilook.data.model.product;

import java.util.List;
import lombok.Data;

@Data public class ProductColorDataModel {
    private String name;
    private String color;
    private boolean soldout;
    private List<ProductSizeDataModel> size;
}