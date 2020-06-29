package com.minilook.minilook.data.model;

import com.minilook.minilook.data.model.product.ProductDataModel;

import java.util.List;

import lombok.Data;

@Data public class BridgeDataModel {
    private String keyword;
    private int total;
    private List<ProductDataModel> products;
}
