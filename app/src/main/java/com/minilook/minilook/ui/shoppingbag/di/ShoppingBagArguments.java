package com.minilook.minilook.ui.shoppingbag.di;

import com.minilook.minilook.ui.shoppingbag.ShoppingBagPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShoppingBagArguments {
    private final ShoppingBagPresenter.View view;
}