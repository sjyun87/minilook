package com.minilook.minilook.ui.preorder_product_detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.ColorView;
import com.minilook.minilook.ui.base.widget.SizeView;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.preorder_product_detail.adapter.PreorderProductDetailImageAdapter;
import com.minilook.minilook.ui.preorder_product_detail.di.PreorderProductDetailArguments;
import com.nex3z.flowlayout.FlowLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class PreorderProductDetailActivity extends BaseActivity implements PreorderProductDetailPresenter.View {

    public static void start(Context context, String title, int preorderNo, int productNo) {
        Intent intent = new Intent(context, PreorderProductDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("title", title);
        intent.putExtra("preorderNo", preorderNo);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.vp_product_image) ViewPager2 productImageViewPager;
    @BindView(R.id.indicator) DotsIndicator indicator;
    @BindView(R.id.txt_index) TextView indexTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.layout_option_color_panel) FlowLayout colorPanel;
    @BindView(R.id.layout_option_size_panel) FlowLayout sizePanel;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.web_product_detail) WebView productDetailWebView;
    @BindView(R.id.txt_info_style_no) TextView infoStyleNoTextView;
    @BindView(R.id.txt_info_kc_auth) TextView infoKcAuthTextView;
    @BindView(R.id.txt_info_weight) TextView infoWeightTextView;
    @BindView(R.id.txt_info_color) TextView infoColorTextView;
    @BindView(R.id.txt_info_material) TextView infoMaterialTextView;
    @BindView(R.id.txt_info_age) TextView infoAgeTextView;
    @BindView(R.id.txt_info_release_date) TextView infoReleaseDateTextView;
    @BindView(R.id.txt_info_manufacturer) TextView infoManufacturerTextView;
    @BindView(R.id.txt_info_country) TextView infoCountryTextView;
    @BindView(R.id.txt_info_caution) TextView infoCautionTextView;
    @BindView(R.id.txt_info_warranty) TextView infoWarrantyTextView;
    @BindView(R.id.txt_info_damage) TextView infoDamageTextView;
    @BindView(R.id.txt_info_service_center) TextView infoServiceCenterTextView;
    @BindView(R.id.layout_info_more_panel) LinearLayout infoMorePanel;
    @BindView(R.id.txt_expand) TextView expandTextView;
    @BindView(R.id.img_expand) ImageView expandImageView;

    @BindString(R.string.base_price_percent) String format_percent;
    @BindString(R.string.product_detail_info_expand) String str_expand;
    @BindString(R.string.product_detail_info_collapse) String str_collapse;

    @BindDrawable(R.drawable.ic_arrow_down_xs) Drawable img_arrow_down;
    @BindDrawable(R.drawable.ic_arrow_up_xs) Drawable img_arrow_up;

    private PreorderProductDetailPresenter presenter;
    private PreorderProductDetailImageAdapter imageAdapter = new PreorderProductDetailImageAdapter();
    private BaseAdapterDataView<String> imageAdapterView = imageAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_preorder_product_detail;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderProductDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderProductDetailArguments provideArguments() {
        return PreorderProductDetailArguments.builder()
            .view(this)
            .title(getIntent().getStringExtra("title"))
            .preorderNo(getIntent().getIntExtra("preorderNo", -1))
            .productNo(getIntent().getIntExtra("productNo", -1))
            .imageAdapter(imageAdapter)
            .build();
    }

    @Override public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    @Override public void setupViewPager() {
        productImageViewPager.setAdapter(imageAdapter);
        indicator.setViewPager2(productImageViewPager);
    }

    @Override public void imageRefresh() {
        imageAdapterView.refresh();
    }

    @Override public void setProductIndex(String title) {
        indexTextView.setText(title);
    }

    @Override public void setProductName(String name) {
        productNameTextView.setText(name);
    }

    @Override public void addColorView(ProductStockDataModel model) {
        ColorView colorView = ColorView.builder()
            .context(this)
            .model(model)
            .build();
        colorPanel.addView(colorView);
    }

    @Override public void addSizeView(ProductStockDataModel model) {
        SizeView sizeView = SizeView.builder()
            .context(this)
            .model(model)
            .build();
        sizePanel.addView(sizeView);
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

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        productDetailWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        productDetailWebView.getSettings().setJavaScriptEnabled(false);
        productDetailWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        productDetailWebView.getSettings().setAppCacheEnabled(true);
        productDetailWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        productDetailWebView.getSettings().setDomStorageEnabled(true);
        productDetailWebView.getSettings().setSupportMultipleWindows(false);
        productDetailWebView.getSettings().setSupportZoom(true);
        productDetailWebView.getSettings().setBuiltInZoomControls(true);
        productDetailWebView.setWebViewClient(new WebViewClient());
        productDetailWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override public void setupProductDetail(String url) {
        productDetailWebView.loadUrl(url);
    }

    @Override public void setupInfoStyleNo(String text) {
        infoStyleNoTextView.setText(text);
    }

    @Override public void setupInfoKcAuth(String text) {
        infoKcAuthTextView.setText(text);
    }

    @Override public void setupInfoWeight(String text) {
        infoWeightTextView.setText(text);
    }

    @Override public void setupInfoColor(String text) {
        infoColorTextView.setText(text);
    }

    @Override public void setupInfoMaterial(String text) {
        infoMaterialTextView.setText(text);
    }

    @Override public void setupInfoAge(String text) {
        infoAgeTextView.setText(text);
    }

    @Override public void setupInfoReleaseDate(String text) {
        infoReleaseDateTextView.setText(text);
    }

    @Override public void setupInfoManufacturer(String text) {
        infoManufacturerTextView.setText(text);
    }

    @Override public void setupInfoCountry(String text) {
        infoCountryTextView.setText(text);
    }

    @Override public void setupInfoCaution(String text) {
        infoCautionTextView.setText(text);
    }

    @Override public void setupInfoWarranty(String text) {
        infoWarrantyTextView.setText(text);
    }

    @Override public void setupInfoDamage(String text) {
        infoDamageTextView.setText(text);
    }

    @Override public void setupInfoServiceCenter(String text) {
        infoServiceCenterTextView.setText(text);
    }

    @Override public void expandInfoMorePanel() {
        infoMorePanel.setVisibility(View.VISIBLE);
        expandTextView.setText(str_collapse);
        expandImageView.setImageDrawable(img_arrow_up);
    }

    @Override public void collapseInfoMorePanel() {
        infoMorePanel.setVisibility(View.GONE);
        expandTextView.setText(str_expand);
        expandImageView.setImageDrawable(img_arrow_down);
    }

    @OnClick(R.id.layout_expand_panel)
    void onExpandClick() {
        presenter.onExpandClick();
    }
}
