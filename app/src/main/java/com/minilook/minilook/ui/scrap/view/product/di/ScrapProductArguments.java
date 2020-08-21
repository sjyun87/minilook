package com.minilook.minilook.ui.scrap.view.product.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrap.view.product.ScrapProductPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapProductArguments {
    private final ScrapProductPresenter.View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
}