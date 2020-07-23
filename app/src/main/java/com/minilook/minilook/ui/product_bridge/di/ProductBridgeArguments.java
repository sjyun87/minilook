package com.minilook.minilook.ui.product_bridge.di;

import com.minilook.minilook.ui.product_bridge.ProductBridgePresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductBridgeArguments {
    private final ProductBridgePresenter.View view;
}