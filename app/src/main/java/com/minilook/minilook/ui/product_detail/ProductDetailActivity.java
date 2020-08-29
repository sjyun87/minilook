package com.minilook.minilook.ui.product_detail;

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
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductSizeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.ColorView;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.option_selector.OptionSelector;
import com.minilook.minilook.ui.product_detail.widget.ProductTabView;
import com.minilook.minilook.ui.base.widget.SizeView;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.SpannableUtil;
import com.nex3z.flowlayout.FlowLayout;
import java.util.List;
import java.util.Objects;
import me.didik.component.StickyNestedScrollView;
import timber.log.Timber;

public class ProductDetailActivity extends BaseActivity implements ProductDetailPresenter.View {

    public static void start(Context context, int product_id) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("product_id", product_id);
        context.startActivity(intent);
    }

    @BindView(R.id.nsv_root) StickyNestedScrollView scrollView;
    @BindView(R.id.vp_product_image) ViewPager2 productImageViewPager;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.layout_option_color_panel) FlowLayout colorPanel;
    @BindView(R.id.layout_option_size_panel) FlowLayout sizePanel;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_point_save) TextView pointTextView;
    @BindView(R.id.txt_shipping) TextView shippingTextView;
    @BindView(R.id.txt_shipping_conditional) TextView shippingConditionalTextView;
    @BindView(R.id.txt_shipping_add) TextView shippingAddTextView;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.web_product_detail) WebView productDetailWebView;
    @BindView(R.id.layout_review_panel) LinearLayout reviewPanel;
    @BindView(R.id.txt_review_count) TextView reviewCountTextView;

    @BindView(R.id.layout_question_panel) LinearLayout questionPanel;
    @BindView(R.id.txt_question_count) TextView questionCountTextView;
    @BindView(R.id.layout_shipping_n_refund_panel) LinearLayout shippingNRefundPanel;
    @BindView(R.id.layout_related_panel) LinearLayout relatedPanel;
    @BindView(R.id.rcv_related_product) RecyclerView relatedProductRecyclerView;

    @BindView(R.id.option_selector) OptionSelector optionSelector;

    @BindString(R.string.base_price_percent) String format_percent;
    @BindString(R.string.product_detail_point) String format_point;
    @BindString(R.string.product_detail_point_save) String format_point_save;
    @BindArray(R.array.tab_product_detail) String[] tabNames;

    @BindString(R.string.product_detail_shipping_free) String str_shipping_free;
    @BindString(R.string.product_detail_shipping) String format_shipping;
    @BindString(R.string.product_detail_shipping_conditional) String format_shipping_conditional;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    private ProductDetailPresenter presenter;
    private ProductDetailImageAdapter productImageAdapter = new ProductDetailImageAdapter();
    private BaseAdapterDataView<String> productImageAdapterView = productImageAdapter;
    private ProductAdapter relatedProductAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> relatedProductAdapterView = relatedProductAdapter;

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
            .id(getIntent().getIntExtra("product_id", -1))
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

    @Override public void setupTabLayout() {
        for (String tabName : tabNames) {
            ProductTabView tabView = ProductTabView.builder()
                .context(this)
                .name(tabName)
                .build();

            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tabView);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
                getTabView(tab).setupSelected();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {
                getTabView(tab).setupUnselected();
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
            }
        });

        getTabView(0).setupSelected();
    }

    public ProductTabView getTabView(TabLayout.Tab tab) {
        return (ProductTabView) tab.getCustomView();
    }

    public ProductTabView getTabView(int position) {
        return (ProductTabView) Objects.requireNonNull(tabLayout.getTabAt(position)).getCustomView();
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
        productDetailWebView.setWebViewClient(new WebViewClient());
        productDetailWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override public void setupRelatedProductRecyclerView() {
        relatedProductRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        relatedProductAdapter.setViewType(ProductAdapter.VIEW_TYPE_SIZE_84);
        relatedProductRecyclerView.setAdapter(relatedProductAdapter);
    }

    @Override public void relatedProductRefresh() {
        relatedProductAdapterView.refresh();
    }

    @Override public void showRelatedPanel() {
        relatedPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRelatedPanel() {
        relatedPanel.setVisibility(View.GONE);
    }

    @Override public void setupBrandName(String text) {
        brandNameTextView.setText(text);
    }

    @Override public void setupProductName(String text) {
        productNameTextView.setText(text);
    }

    @Override public void addColorView(ProductStockModel model) {
        ColorView colorView = ColorView.builder()
            .context(this)
            .model(model)
            .build();
        colorPanel.addView(colorView);
    }

    @Override public void addSizeView(ProductStockModel model) {
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

    @Override public void setupPoint(int point) {
        String pointText = String.format(format_point, point);
        String totalText = String.format(format_point_save, pointText);
        SpannableString span = new SpannableString(totalText);
        SpannableUtil.styleSpan(span, pointText, Typeface.BOLD);
        pointTextView.setText(span);
    }

    @Override public void setupShipping(int price) {
        if (price == 0) {
            shippingTextView.setText(str_shipping_free);
        } else {
            shippingTextView.setText(String.format(format_shipping, price));
        }
    }

    @Override public void setupShippingConditional(int price) {
        shippingConditionalTextView.setText(String.format(format_shipping_conditional, price));
    }

    @Override public void showShippingConditional() {
        shippingConditionalTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingConditional() {
        shippingConditionalTextView.setVisibility(View.GONE);
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

    @Override public void setupReviewCount(String text) {
        reviewCountTextView.setText(text);
        getTabView(1).setupCount(text);
    }

    @Override public void setupQuestionCount(String text) {
        questionCountTextView.setText(text);
        getTabView(2).setupCount(text);
    }

    @Override public void setupOptionSelector(List<ProductColorDataModel> data) {
        optionSelector.setupData(data);
    }

    @Override public void showOptionSelector() {
        optionSelector.show();
    }

    @Override public void hideOptionSelector() {
        optionSelector.hide();
    }

    @Override public void navigateToBrandDetail(int brand_id) {
        BrandDetailActivity.start(this, brand_id);
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        presenter.onBrandClick();
    }

    @OnClick(R.id.txt_buy)
    void onBuyClick() {
        presenter.onBuyClick();
    }
}
