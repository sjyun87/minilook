package com.minilook.minilook.ui.recent.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.recent.RecentPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class RecentArguments {
    private final RecentPresenter.View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
}