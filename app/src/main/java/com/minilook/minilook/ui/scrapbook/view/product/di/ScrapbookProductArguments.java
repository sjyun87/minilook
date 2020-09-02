package com.minilook.minilook.ui.scrapbook.view.product.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.scrapbook.view.product.ScrapbookProductPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapbookProductArguments {
    private final ScrapbookProductPresenter.View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
}