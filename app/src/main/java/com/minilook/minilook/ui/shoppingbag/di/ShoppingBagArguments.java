package com.minilook.minilook.ui.shoppingbag.di;

import com.minilook.minilook.data.model.order.ShoppingBagDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ShoppingBagArguments {
    private final ShoppingBagPresenter.View view;
    private final BaseAdapterDataModel<ShoppingBagDataModel> adapter;
}