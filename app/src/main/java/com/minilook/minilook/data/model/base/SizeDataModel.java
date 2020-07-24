package com.minilook.minilook.data.model.base;

import lombok.Data;

@Data public class SizeDataModel {
    private String name;
    private int stock;
    private int price_add;
    private boolean soldout;
}