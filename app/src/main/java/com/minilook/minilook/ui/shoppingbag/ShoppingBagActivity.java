package com.minilook.minilook.ui.shoppingbag;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagArguments;

public class ShoppingBagActivity extends BaseActivity implements ShoppingBagPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingBagActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ShoppingBagPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_preorder_detail;
    }

    @Override protected void createPresenter() {
        presenter = new ShoppingBagPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShoppingBagArguments provideArguments() {
        return ShoppingBagArguments.builder()
            .view(this)
            .build();
    }
}
