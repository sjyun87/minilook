package com.minilook.minilook.ui.product_bridge;

import android.content.Context;
import android.content.Intent;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;

public class ProductBridgeActivity extends BaseActivity implements ProductBridgePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ProductBridgeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ProductBridgePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_product_bridge;
    }

    @Override protected void createPresenter() {
        presenter = new ProductBridgePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductBridgeArguments provideArguments() {
        return ProductBridgeArguments.builder()
            .view(this)
            .build();
    }

}
