package com.minilook.minilook.data.model;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;

import lombok.Data;

@Data public class InfoDataModel {
    private BrandInfoDataModel brand_info;
    private StoreDataModel store_info;
}