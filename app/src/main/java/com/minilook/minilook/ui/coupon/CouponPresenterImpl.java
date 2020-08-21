package com.minilook.minilook.ui.coupon;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.coupon.di.CouponArguments;

public class CouponPresenterImpl extends BasePresenterImpl implements CouponPresenter {

    private final View view;

    public CouponPresenterImpl(CouponArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}