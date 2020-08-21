package com.minilook.minilook.ui.shipping_add;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.shipping_add.di.ShippingAddArguments;

public class ShippingAddActivity extends BaseActivity implements ShippingAddPresenter.View {

    private ShippingAddPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_shipping;
    }

    @Override protected void createPresenter() {
        presenter = new ShippingAddPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShippingAddArguments provideArguments() {
        return ShippingAddArguments.builder()
            .view(this)
            .build();
    }
}
