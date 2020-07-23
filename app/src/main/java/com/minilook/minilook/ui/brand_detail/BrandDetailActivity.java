package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BrandDetailActivity extends BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brand_id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.img_bg) ImageView bgImageView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_cs_date) TextView csDateTextView;
    @BindView(R.id.txt_cs_tel) TextView csTelTextView;
    @BindView(R.id.txt_cs_email) TextView csEmailTextView;
    @BindView(R.id.txt_cs_address) TextView csAddressTextView;

    private BrandDetailPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_brand_detail;
    }

    @Override protected void createPresenter() {
        presenter = new BrandDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BrandDetailArguments provideArguments() {
        return BrandDetailArguments.builder()
            .view(this)
            .brandId(getIntent().getIntExtra("brand_id", -1))
            .build();
    }

    @Override public void setupBgImage(String url) {
        Glide.with(this)
            .load(url)
            .into(bgImageView);
    }

    @Override public void setupDesc(String text) {
        descTextView.setText(text);
    }

    @Override public void setupName(String text) {
        nameTextView.setText(text);
    }

    @Override public void setupCsDate(String text) {
        csDateTextView.setText(text);
    }

    @Override public void setupCsTel(String text) {
        csTelTextView.setText(text);
    }

    @Override public void setupCsEmail(String text) {
        csEmailTextView.setText(text);
    }

    @Override public void setupCsAddress(String text) {
        csAddressTextView.setText(text);
    }

    @OnClick(R.id.txt_more)
    void onMoreClick() {
        ProductBridgeActivity.start(this);
    }
}
