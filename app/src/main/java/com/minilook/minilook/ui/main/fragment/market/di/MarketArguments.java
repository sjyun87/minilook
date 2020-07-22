package com.minilook.minilook.ui.main.fragment.market.di;

import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.main.fragment.market.MarketPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MarketArguments {
    private final MarketPresenter.View view;
    private final BaseAdapterDataModel<MarketDataModel> adapter;
}