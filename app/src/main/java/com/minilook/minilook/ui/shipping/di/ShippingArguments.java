package com.minilook.minilook.ui.shipping.di;

import com.minilook.minilook.ui.shipping.ShippingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShippingArguments {
    private final ShippingPresenter.View view;
}