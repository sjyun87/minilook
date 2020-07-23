package com.minilook.minilook.ui.brand;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.brand.adapter.BrandArguments;

public class BrandActivity extends BaseActivity implements BrandPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, BrandActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private BrandPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_brand;
    }

    @Override protected void createPresenter() {
        presenter = new BrandPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BrandArguments provideArguments() {
        return BrandArguments.builder()
            .view(this)
            .build();
    }
}
