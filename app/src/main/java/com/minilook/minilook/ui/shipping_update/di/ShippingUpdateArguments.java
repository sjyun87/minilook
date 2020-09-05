package com.minilook.minilook.ui.shipping_update.di;

import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.ui.shipping_update.ShippingUpdatePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShippingUpdateArguments {
    private final ShippingUpdatePresenter.View view;
    private final ShippingDataModel shippingData;
}