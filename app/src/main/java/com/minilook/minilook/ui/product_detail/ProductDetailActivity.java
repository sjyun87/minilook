package com.minilook.minilook.ui.product_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.SpannableUtil;

public class ProductDetailActivity extends BaseActivity implements ProductDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.vp_product_image) ViewPager2 productImageViewPager;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_point_save) TextView pointTextView;
    @BindView(R.id.txt_delivery_info) TextView deliveryInfoTextView;

    @BindString(R.string.base_price_percent) String format_percent;
    @BindString(R.string.product_detail_point) String format_point;
    @BindString(R.string.product_detail_point_save) String format_point_save;

    @BindString(R.string.product_detail_delivery_info) String str_delivery_info;
    @BindString(R.string.product_detail_delivery_info_b) String str_delivery_info_b;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    private ProductDetailPresenter presenter;
    private ProductDetailImageAdapter productImageAdapter = new ProductDetailImageAdapter();
    private BaseAdapterDataView<String> productImageAdapterView = productImageAdapter;

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
            .productImageAdapter(productImageAdapter)
            .build();
    }

    @Override public void setupProductImageViewPager() {
        productImageViewPager.setAdapter(productImageAdapter);
    }

    @Override public void productImageRefresh() {
        productImageAdapterView.refresh();
    }

    @Override public void setupBrandName(String text) {
        brandNameTextView.setText(text);
    }

    @Override public void setupProductName(String text) {
        productNameTextView.setText(text);
    }

    @Override public void setupPriceOrigin(String text) {
        priceOriginTextView.setPaintFlags(priceOriginTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceOriginTextView.setText(text);
    }

    @Override public void showPriceOrigin() {
        priceOriginTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hidePriceOrigin() {
        priceOriginTextView.setVisibility(View.GONE);
    }

    @Override public void setupDiscountPercent(int percent) {
        discountPercentTextView.setText(String.format(format_percent, percent));
    }

    @Override public void showDiscountPercent() {
        discountPercentTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideDiscountPercent() {
        discountPercentTextView.setVisibility(View.GONE);
    }

    @Override public void setupPrice(String text) {
        priceTextView.setText(text);
    }

    @Override public void setupPoint(int point) {
        String pointText = String.format(format_point, point);
        String totalText = String.format(format_point_save, pointText);
        SpannableString span = new SpannableString(totalText);
        SpannableUtil.styleSpan(span, pointText, Typeface.BOLD);
        pointTextView.setText(span);
    }

    @Override public void setupDeliveryInfoTextView() {
        SpannableString span = new SpannableString(str_delivery_info);
        SpannableUtil.styleSpan(span, str_delivery_info_b, Typeface.BOLD);
        SpannableUtil.foregroundColorSpan(span, str_delivery_info_b, color_FF8140E5);
        deliveryInfoTextView.setText(span);
    }
}
