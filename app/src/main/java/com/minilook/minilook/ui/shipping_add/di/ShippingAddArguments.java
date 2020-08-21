package com.minilook.minilook.ui.shipping_add.di;

import com.minilook.minilook.ui.shipping_add.ShippingAddPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShippingAddArguments {
    private final ShippingAddPresenter.View view;
}