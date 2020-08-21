package com.minilook.minilook.ui.shipping_edit.di;

import com.minilook.minilook.ui.shipping_edit.ShippingEditPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShippingEditArguments {
    private final ShippingEditPresenter.View view;
}