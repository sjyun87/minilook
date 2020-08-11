package com.minilook.minilook.ui.lookbook.view.detail.di;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LookBookDetailArguments {
    private final LookBookDetailPresenter.View view;
    private final BaseAdapterDataModel<ImageDataModel> styleAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
}