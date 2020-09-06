package com.minilook.minilook.ui.product_bridge.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.product_bridge.ProductBridgePresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductBridgeArguments {
    private final ProductBridgePresenter.View view;
    private final SearchOptionDataModel options;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
}