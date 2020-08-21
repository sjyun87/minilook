package com.minilook.minilook.ui.shipping_add;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.profile.ProfileActivity;
import com.minilook.minilook.ui.shipping_add.di.ShippingAddArguments;

public class ShippingAddActivity extends BaseActivity implements ShippingAddPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShippingAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ShippingAddPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_shipping_add;
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
