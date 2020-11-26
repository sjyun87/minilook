package com.minilook.minilook.ui.product_info;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.product_info.di.ProductInfoArguments;

public class ProductInfoActivity extends _BaseActivity implements ProductInfoPresenter.View {

    public static void start(Context context, int brand_id) {
        Intent intent = new Intent(context, ProductInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brand_id", brand_id);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_guide) TextView guideTextView;

    private ProductInfoPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_product_info;
    }

    @Override protected void createPresenter() {
        presenter = new ProductInfoPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductInfoArguments provideArguments() {
        return ProductInfoArguments.builder()
            .view(this)
            .brand_id(getIntent().getIntExtra("brand_id", -1))
            .build();
    }

    @Override public void setupGuide(String text) {
        guideTextView.setText(text);
    }
}
