package com.minilook.minilook.ui.coupon.di;

import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.coupon.CouponPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CouponArguments {
    private final CouponPresenter.View view;
    private final BaseAdapterDataModel<CouponDataModel> adapter;
}