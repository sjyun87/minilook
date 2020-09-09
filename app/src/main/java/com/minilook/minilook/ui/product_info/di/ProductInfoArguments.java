package com.minilook.minilook.ui.product_info.di;

import com.minilook.minilook.ui.product_info.ProductInfoPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductInfoArguments {
    private final ProductInfoPresenter.View view;
    private final int brand_id;
}