package com.minilook.minilook.ui.shipping_edit;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.shipping_edit.di.ShippingEditArguments;

public class ShippingEditActivity extends BaseActivity implements ShippingEditPresenter.View {

    private ShippingEditPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_shipping_edit;
    }

    @Override protected void createPresenter() {
        presenter = new ShippingEditPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShippingEditArguments provideArguments() {
        return ShippingEditArguments.builder()
            .view(this)
            .build();
    }
}
