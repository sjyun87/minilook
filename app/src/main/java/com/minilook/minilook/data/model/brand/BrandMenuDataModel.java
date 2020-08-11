package com.minilook.minilook.data.model.brand;

import lombok.Data;

@Data public class BrandMenuDataModel {
    private BrandDataModel model;
    private int position;
    private boolean isSelect;
}