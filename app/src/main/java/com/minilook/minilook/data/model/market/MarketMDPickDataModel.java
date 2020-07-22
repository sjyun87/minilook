package com.minilook.minilook.data.model.market;

import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class MarketMDPickDataModel {
    private String category;
    private List<ProductDataModel> products;
}
