package com.minilook.minilook.ui.promotion_detail;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.promotion_detail.adapter.PromotionAdapter;
import com.minilook.minilook.ui.promotion_detail.di.PromotionDetailArguments;

public class PromotionDetailActivity extends BaseActivity implements PromotionDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, PromotionDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("promotion_id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.img_bg) ImageView bgImageView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_goods) RecyclerView productRecyclerView;

    private PromotionDetailPresenter presenter;
    private PromotionAdapter adapter = new PromotionAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_promotion;
    }

    @Override protected void createPresenter() {
        presenter = new PromotionDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PromotionDetailArguments provideArguments() {
        return PromotionDetailArguments.builder()
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
