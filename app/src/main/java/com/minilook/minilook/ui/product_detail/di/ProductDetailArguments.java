package com.minilook.minilook.ui.product_detail.di;

import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.ProductDetailPresenter;

import com.minilook.minilook.ui.product_detail.adapter.ProductColorAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductDetailArguments {
    private final ProductDetailPresenter.View view;
    private final int id;
    private final ProductDetailImageAdapter productImageAdapter;
    private final ProductAdapter relatedProductAdapter;

    private final ProductColorAdapter colorAdapter;
}