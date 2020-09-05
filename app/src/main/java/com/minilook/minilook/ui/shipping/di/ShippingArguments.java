package com.minilook.minilook.ui.shipping.di;

import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.shipping.ShippingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShippingArguments {
    private final ShippingPresenter.View view;
    private final String route;
    private final BaseAdapterDataModel<ShippingDataModel> adapter;
}