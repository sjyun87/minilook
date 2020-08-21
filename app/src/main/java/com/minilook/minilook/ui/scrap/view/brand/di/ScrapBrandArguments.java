package com.minilook.minilook.ui.scrap.view.brand.di;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.scrap.view.brand.ScrapBrandPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapBrandArguments {
    private final ScrapBrandPresenter.View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
}