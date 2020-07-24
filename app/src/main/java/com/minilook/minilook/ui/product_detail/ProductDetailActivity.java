package com.minilook.minilook.ui.product_detail;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;

public class ProductDetailActivity extends BaseActivity implements ProductDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private ProductDetailPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_product_detail;
    }

    @Override protected void createPresenter() {
        presenter = new ProductDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductDetailArguments provideArguments() {
        return ProductDetailArguments.builder()
            .view(this)
            .id(getIntent().getIntExtra("id", -1))
            .build();
    }
}
