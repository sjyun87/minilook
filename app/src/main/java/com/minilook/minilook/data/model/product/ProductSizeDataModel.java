package com.minilook.minilook.data.model.product;

import lombok.Data;

@Data public class ProductSizeDataModel {
    private String name;
    private int stock;
    private int price_add;
    private boolean soldout;
}