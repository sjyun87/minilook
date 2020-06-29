package com.minilook.minilook.data.model.promotion;

import com.minilook.minilook.data.model.product.ProductDataModel;

import java.util.List;

import lombok.Data;

@Data public class PromotionDataModel {
    private int id;
    private String title;
    private String desc;
    private String image_url;
    private String image_thumb_url;
    private List<ProductDataModel> products;
}
