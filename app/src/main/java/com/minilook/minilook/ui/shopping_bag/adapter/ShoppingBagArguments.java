package com.minilook.minilook.ui.shopping_bag.adapter;

import com.minilook.minilook.ui.shopping_bag.ShoppingBagPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShoppingBagArguments {
    private final ShoppingBagPresenter.View view;
}