package com.minilook.minilook.ui.product_detail.di;

import com.minilook.minilook.ui.product_detail.ProductDetailPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductDetailArguments {
    private final ProductDetailPresenter.View view;
    private final int id;
}