package com.minilook.minilook.ui.order.di;

import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.order.OrderPresenter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderArguments {
    private final OrderPresenter.View view;
    private final List<ShoppingBrandDataModel> items;
    private final BaseAdapterDataModel<ShoppingBrandDataModel> orderAdapter;
    private final BaseAdapterDataModel<CouponDataModel> couponAdapter;
}