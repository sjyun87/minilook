package com.minilook.minilook.ui.product_detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductOptionDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.ColorView;
import com.minilook.minilook.ui.base.widget.SizeView;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.option_selector.OptionSelector;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.ui.product_detail.widget.ProductTabView;
import com.minilook.minilook.ui.review.adapter.ReviewAdapter;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import com.nex3z.flowlayout.FlowLayout;
import java.util.List;
import java.util.Objects;
import me.didik.component.StickyNestedScrollView;

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
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.txt_point_save) TextView pointTextView;
    @BindView(R.id.txt_shipping) TextView shippingTextView;
    @BindView(R.id.txt_shipping_conditional) TextView shippingConditionalTextView;
    @BindView(R.id.txt_shipping_add) TextView shippingAddTextView;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.web_product_detail) WebView productDetailWebView;
    @BindView(R.id.layout_review_contents_panel) LinearLayout reviewContentsPanel;
    @BindView(R.id.txt_review_more) TextView reviewMoreTextView;
    @BindView(R.id.rcv_review) RecyclerView reviewRecyclerView;
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

    @BindView(R.id.layout_review_panel) LinearLayout reviewPanel;
    @BindView(R.id.txt_review_count) TextView reviewCountTextView;

    @BindView(R.id.layout_question_panel) LinearLayout questionPanel;
    @BindView(R.id.txt_question_count) TextView questionCountTextView;
    @BindView(R.id.layout_shipping_n_refund_panel) LinearLayout shippingNRefundPanel;
    @BindView(R.id.layout_related_panel) LinearLayout relatedPanel;
    @BindView(R.id.rcv_related_product) RecyclerView relatedProductRecyclerView;

    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_buy) TextView buyTextView;

    @BindView(R.id.option_selector) OptionSelector optionSelector;

    @BindString(R.string.base_price_percent) String format_percent;
    @BindString(R.string.product_detail_point) String format_point;
    @BindString(R.string.product_detail_point_save) String format_point_save;
    @BindString(R.string.product_detail_review_more) String format_review_more;
    @BindArray(R.array.tab_product_detail) String[] tabNames;

    @BindString(R.string.product_detail_shipping_free) String str_shipping_free;
    @BindString(R.string.product_detail_shipping) String format_shipping;
    @BindString(R.string.product_detail_shipping_conditional) String format_shipping_conditional;

    @BindString(R.string.product_detail_info_expand) String str_expand;
    @BindString(R.string.product_detail_info_collapse) String str_collapse;

    @BindDrawable(R.drawable.ic_arrow_down_xs) Drawable img_arrow_down;
    @BindDrawable(R.drawable.ic_arrow_up_xs) Drawable img_arrow_up;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    private ProductDetailPresenter presenter;
    private ProductDetailImageAdapter productImageAdapter = new ProductDetailImageAdapter();
    private BaseAdapterDataView<String> productImageAdapterView = productImageAdapter;
    private ProductAdapter relatedProductAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> relatedProductAdapterView = relatedProductAdapter;
    private ReviewAdapter reviewAdapter = new ReviewAdapter();
    private BaseAdapterDataView<ReviewDataModel> reviewAdapterView = reviewAdapter;

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
            .reviewAdapter(reviewAdapter)
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

    @Override public void setupReviewRecyclerView() {
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(reviewAdapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(color_FFDBDBDB)
            .showFirstDivider()
            .build()
            .addTo(reviewRecyclerView);
    }

    @Override public void reviewRefresh() {
        reviewAdapterView.refresh();
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

    @Override public void setupShippingFree() {
        shippingTextView.setText(str_shipping_free);
    }

    @Override public void setupShippingPrice(int price) {
        shippingTextView.setText(String.format(format_shipping, StringUtil.toDigit(price)));
    }

    @Override public void setupShippingCondition(int price) {
        shippingConditionalTextView.setText(String.format(format_shipping_conditional, StringUtil.toDigit(price)));
    }

    @Override public void showShippingCondition() {
        shippingConditionalTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingCondition() {
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
        reviewMoreTextView.setText(String.format(format_review_more, text));
    }

    @Override public void showReviewContentsPanel() {
        reviewContentsPanel.setVisibility(View.VISIBLE);
    }

    @Override public void setupQuestionCount(String text) {
        questionCountTextView.setText(text);
        getTabView(2).setupCount(text);
    }

    @Override public void setupOptionSelector(int price, List<ProductOptionDataModel> options) {
        optionSelector.setupData(price, options);
        optionSelector.setOnButtonClickListener(new OptionSelector.OnButtonClickListener() {
            @Override public void onShoppingBagClick(List<OrderOptionDataModel> goodsData) {
                presenter.onShoppingBagClick(goodsData);
            }

            @Override public void onBuyClick(List<OrderOptionDataModel> goodsData) {

            }
        });
    }

    @Override public void showOptionSelector() {
        optionSelector.show();
    }

    @Override public void hideOptionSelector() {
        optionSelector.hide();
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

    @Override public void showDisplayLabel(String label) {
        displayLabelTextView.setText(label);
        displayLabelTextView.setVisibility(View.VISIBLE);
    }

    @Override public void disableBuyButton(String label) {
        buyTextView.setText(label);
        buyTextView.setBackgroundColor(color_FFDBDBDB);
        buyTextView.setEnabled(false);
    }

    @Override public void setupPriceOriginNoDisplayColor() {
        priceOriginTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupDiscountPercentNoDisplayColor() {
        discountPercentTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setupPriceNoDisplayColor() {
        priceTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void hideScrap() {
        scrapImageView.setVisibility(View.GONE);
    }

    @Override public void navigateToBrandDetail(int brand_id) {
        BrandDetailActivity.start(this, brand_id);
    }

    @Override public void navigateToShoppingBag() {
        ShoppingBagActivity.start(this);
    }

    @OnClick(R.id.layout_expand_panel)
    void onExpandClick() {
        presenter.onExpandClick();
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
