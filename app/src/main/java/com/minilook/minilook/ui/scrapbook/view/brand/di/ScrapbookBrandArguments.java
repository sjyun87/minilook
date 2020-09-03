package com.minilook.minilook.ui.scrapbook.view.brand.di;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.scrapbook.view.brand.ScrapbookBrandPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapbookBrandArguments {
    private final ScrapbookBrandPresenter.View view;
    private final BaseAdapterDataModel<BrandDataModel> adapter;
}