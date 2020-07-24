package com.minilook.minilook.ui.product_detail;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductColorAdapter;
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

    @BindView(R.id.nsv_root) StickyScrollView scrollView;
    @BindView(R.id.vp_product_image) ViewPager2 productImageViewPager;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.rcv_option_color) RecyclerView colorRecyclerView;
    @BindView(R.id.rcv_option_size) RecyclerView sizeRecyclerView;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_point_save) TextView pointTextView;
    @BindView(R.id.txt_delivery_info) TextView deliveryInfoTextView;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.web_product_detail) WebView productDetailWebView;
    @BindView(R.id.layout_review_panel) LinearLayout reviewPanel;
    @BindView(R.id.txt_review_count) TextView reviewCountTextView;

    @BindView(R.id.layout_question_panel) LinearLayout questionPanel;
    @BindView(R.id.txt_question_count) TextView questionCountTextView;
    @BindView(R.id.layout_shipping_n_refund_panel) LinearLayout shippingNRefundPanel;
    @BindView(R.id.rcv_related_product) RecyclerView relatedProductRecyclerView;

    @BindView(R.id.curtain) View curtainView;
    @BindView(R.id.layout_buy_panel) LinearLayout buyPanel;

    @BindString(R.string.base_price_percent) String format_percent;
    @BindString(R.string.product_detail_point) String format_point;
    @BindString(R.string.product_detail_point_save) String format_point_save;

    @BindString(R.string.product_detail_delivery_info) String str_delivery_info;
    @BindString(R.string.product_detail_delivery_info_b) String str_delivery_info_b;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    private ProductDetailPresenter presenter;
    private ProductDetailImageAdapter productImageAdapter = new ProductDetailImageAdapter();
    private BaseAdapterDataView<String> productImageAdapterView = productImageAdapter;
    private ProductAdapter relatedProductAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> relatedProductAdapterView = relatedProductAdapter;

    private ProductColorAdapter colorAdapter = new ProductColorAdapter();


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
            .relatedProductAdapter(relatedProductAdapter)
            .build();
    }

    @Override public void setupProductImageViewPager() {
        productImageViewPager.setAdapter(productImageAdapter);
    }

    @Override public void productImageRefresh() {
        productImageAdapterView.refresh();
    }

    @Override public void setupColorRecyclerView() {
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        colorRecyclerView.setAdapter(colorAdapter);
    }

    @Override public void setupSizeRecyclerView() {
        sizeRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    @Override public void setupTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        productDetailWebView.getSettings().setJavaScriptEnabled(false);
        productDetailWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        productDetailWebView.getSettings().setAppCacheEnabled(false);
        productDetailWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        productDetailWebView.getSettings().setDomStorageEnabled(true);
        productDetailWebView.getSettings().setSupportMultipleWindows(false);
        productDetailWebView.getSettings().setUseWideViewPort(true);
        productDetailWebView.setWebViewClient(new WebViewClient());
        productDetailWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override public void setupRelatedProductRecyclerView() {
        relatedProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        relatedProductAdapter.setViewType(ProductAdapter.VIEW_TYPE_MEDIUM);
        relatedProductRecyclerView.setAdapter(relatedProductAdapter);
    }

    @Override public void relatedProductRefresh() {
        relatedProductAdapterView.refresh();
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

    @Override public void scrollToProductInfo() {
        scrollView.smoothScrollTo(0, (int) productDetailWebView.getY() - tabLayout.getHeight());
    }

    @Override public void scrollToReview() {
        scrollView.smoothScrollTo(0, (int) reviewPanel.getY() - tabLayout.getHeight());
    }

    @Override public void scrollToQuestion() {
        scrollView.smoothScrollTo(0, (int) questionPanel.getY() - tabLayout.getHeight());
    }

    @Override public void scrollToShippingNRefund() {
        scrollView.smoothScrollTo(0, (int) shippingNRefundPanel.getY() - tabLayout.getHeight());
    }

    @Override public void setupProductDetail(String url) {
        productDetailWebView.loadUrl(url);
    }

    @Override public void setupQuestionCount(String text) {
        questionCountTextView.setText(text);
    }

    @Override public void showCurtain() {
        curtainView.setVisibility(View.VISIBLE);
    }

    @Override public void hideCurtain() {
        curtainView.setVisibility(View.GONE);
    }

    @Override public void showBuyPanel() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> buyPanel.setVisibility(View.VISIBLE))
            .playOn(buyPanel);
    }

    @Override public void hideBuyPanel() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> {
                buyPanel.setVisibility(View.GONE);
                hideCurtain();
            })
            .playOn(buyPanel);
    }

    @OnClick(R.id.txt_buy)
    void onBuyClick() {
        presenter.onBuyClick();
    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        presenter.onCurtainClick();
    }
}
