package com.minilook.minilook.ui.promotion;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.promotion.adapter.PromotionAdapter;
import com.minilook.minilook.ui.promotion.di.PromotionArguments;

import butterknife.BindView;

public class PromotionActivity extends BaseActivity implements PromotionPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, PromotionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("promotion_id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.img_bg) ImageView bgImageView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;;

    private PromotionPresenter presenter;
    private PromotionAdapter adapter = new PromotionAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_promotion;
    }

    @Override protected void createPresenter() {
        presenter = new PromotionPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PromotionArguments provideArguments() {
        return PromotionArguments.builder()
            .view(this)
            .promotionId(getIntent().getIntExtra("promotion_id", -1))
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(getResources().getDimensionPixelSize(R.dimen.dp_10))
            .asSpace()
            .build()
            .addTo(productRecyclerView);
    }

    @Override public void setupBgImage(String url) {
        Glide.with(this)
            .load(url)
            .into(bgImageView);
    }

    @Override public void setupDesc(String text) {
        descTextView.setText(text);
    }

    @Override public void setupTitle(String text) {
        titleTextView.setText(text);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
