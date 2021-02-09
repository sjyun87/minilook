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
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.databinding.ActivityProductDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.ColorView;
import com.minilook.minilook.ui.base.widget.SizeView;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.order.OrderActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import com.minilook.minilook.ui.product_detail.adapter.ProductDetailReviewAdapter;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.ui.product_detail.widget.ProductTabView;
import com.minilook.minilook.ui.product_info.ProductInfoActivity;
import com.minilook.minilook.ui.product_option_selector.ProductOptionSelector;
import com.minilook.minilook.ui.question.QuestionActivity;
import com.minilook.minilook.ui.review.ReviewActivity;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.List;
import java.util.Objects;

public class ProductDetailActivity extends BaseActivity implements ProductDetailPresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @StringRes int format_percent = R.string.base_price_percent;
    @StringRes int format_point = R.string.product_detail_point;
    @StringRes int format_point_save = R.string.product_detail_point_save;
    @StringRes int format_review_more = R.string.product_detail_review_more;
    @StringRes int str_error_msg = R.string.dialog_error_title;
    @ArrayRes int tabNames = R.array.tab_product_detail;

    @StringRes int str_shipping_free = R.string.product_detail_shipping_free;
    @StringRes int format_shipping = R.string.product_detail_shipping;
    @StringRes int format_shipping_conditional = R.string.product_detail_shipping_conditional;

    @StringRes int str_expand = R.string.product_detail_info_expand;
    @StringRes int str_collapse = R.string.product_detail_info_collapse;
    @StringRes int str_add_shoppingbag = R.string.toast_add_shoppingbag;

    @DrawableRes int img_arrow_down = R.drawable.ic_arrow_down_xs;
    @DrawableRes int img_arrow_up = R.drawable.ic_arrow_up_xs;
    @DrawableRes int img_scrap_off = R.drawable.ic_scrap_off;
    @DrawableRes int img_scrap_on = R.drawable.ic_scrap_on;

    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;
    @ColorRes int color_FFA9A9A9 = R.color.color_FFA9A9A9;
    @ColorRes int color_FFDBDBDB = R.color.color_FFDBDBDB;

    private ActivityProductDetailBinding binding;
    private ProductDetailPresenter presenter;

    private final ProductDetailImageAdapter productImageAdapter = new ProductDetailImageAdapter();
    private final BaseAdapterDataView<String> productImageAdapterView = productImageAdapter;
    private final ProductAdapter relatedProductAdapter = new ProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> relatedProductAdapterView = relatedProductAdapter;
    private final ProductDetailReviewAdapter reviewAdapter = new ProductDetailReviewAdapter();
    private final BaseAdapterDataView<ReviewDataModel> reviewAdapterView = reviewAdapter;

    @Override protected View getBindingView() {
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ProductDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductDetailArguments provideArguments() {
        return ProductDetailArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .productImageAdapter(productImageAdapter)
            .reviewAdapter(reviewAdapter)
            .relatedProductAdapter(relatedProductAdapter)
            .build();
    }

    @Override public void setupProductImageViewPager() {
        binding.vpProductImage.setAdapter(productImageAdapter);
        binding.indicator.setViewPager2(binding.vpProductImage);
    }

    @Override public void productImageRefresh() {
        productImageAdapterView.refresh();
    }

    @Override public void setupTabLayout() {
        for (String tabName : resources.getStringArray(tabNames)) {
            ProductTabView tabView = ProductTabView.builder()
                .context(this)
                .name(tabName)
                .build();

            TabLayout.Tab tab = binding.layoutTabPanel.newTab();
            tab.setCustomView(tabView);
            binding.layoutTabPanel.addTab(tab);
        }

        binding.layoutTabPanel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        return (ProductTabView) Objects.requireNonNull(binding.layoutTabPanel.getTabAt(position)).getCustomView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        binding.webProductDetail.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        binding.webProductDetail.getSettings().setJavaScriptEnabled(false);
        binding.webProductDetail.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        binding.webProductDetail.getSettings().setAppCacheEnabled(true);
        binding.webProductDetail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        binding.webProductDetail.getSettings().setDomStorageEnabled(true);
        binding.webProductDetail.getSettings().setSupportMultipleWindows(false);
        binding.webProductDetail.getSettings().setSupportZoom(true);
        binding.webProductDetail.getSettings().setBuiltInZoomControls(true);
        binding.webProductDetail.setWebViewClient(new WebViewClient());
        binding.webProductDetail.setWebChromeClient(new WebChromeClient());
    }

    @Override public void setupRelatedProductRecyclerView() {
        binding.rcvRelatedProduct.setHasFixedSize(true);
        binding.rcvRelatedProduct.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        relatedProductAdapter.setViewType(ProductAdapter.VIEW_TYPE_SIZE_84);
        binding.rcvRelatedProduct.setAdapter(relatedProductAdapter);
    }

    @Override public void relatedProductRefresh() {
        relatedProductAdapterView.refresh();
    }

    @Override public void setupReviewRecyclerView() {
        binding.rcvReview.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvReview.setAdapter(reviewAdapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(color_FFDBDBDB)
            .showFirstDivider()
            .build()
            .addTo(binding.rcvReview);
    }

    @Override public void reviewRefresh() {
        reviewAdapterView.refresh();
    }

    @Override public void showRelatedPanel() {
        binding.layoutRelatedPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRelatedPanel() {
        binding.layoutRelatedPanel.setVisibility(View.GONE);
    }

    @Override public void setupBrandName(String text) {
        binding.txtBrandName.setText(text);
    }

    @Override public void setupProductName(String text) {
        binding.txtBrandName.setText(text);
    }

    @Override public void addColorView(ProductStockDataModel model) {
        ColorView colorView = ColorView.builder()
            .context(this)
            .model(model)
            .build();
        binding.layoutOptionColorPanel.addView(colorView);
    }

    @Override public void addSizeView(ProductStockDataModel model) {
        SizeView sizeView = SizeView.builder()
            .context(this)
            .model(model)
            .build();
        binding.layoutOptionSizePanel.addView(sizeView);
    }

    @Override public void setupPriceOrigin(String text) {
        binding.txtPriceOrigin.setPaintFlags(binding.txtPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.txtPriceOrigin.setText(text);
    }

    @Override public void showPriceOrigin() {
        binding.txtPriceOrigin.setVisibility(View.VISIBLE);
    }

    @Override public void hidePriceOrigin() {
        binding.txtPriceOrigin.setVisibility(View.GONE);
    }

    @Override public void setupDiscountPercent(int percent) {
        binding.txtDiscountPercent.setText(String.format(resources.getString(format_percent), percent));
    }

    @Override public void showDiscountPercent() {
        binding.txtDiscountPercent.setVisibility(View.VISIBLE);
    }

    @Override public void hideDiscountPercent() {
        binding.txtDiscountPercent.setVisibility(View.GONE);
    }

    @Override public void setupPrice(String text) {
        binding.txtPrice.setText(text);
    }

    @Override public void setupPoint(int point) {
        String pointText = String.format(resources.getString(format_point), point);
        String totalText = String.format(resources.getString(format_point_save), pointText);
        SpannableString span = new SpannableString(totalText);
        SpannableUtil.styleSpan(span, pointText, Typeface.BOLD);
        binding.txtPointSave.setText(span);
    }

    @Override public void setupShippingFree() {
        binding.txtShipping.setText(str_shipping_free);
    }

    @Override public void setupShippingPrice(int price) {
        binding.txtShipping.setText(String.format(resources.getString(format_shipping), StringUtil.toDigit(price)));
    }

    @Override public void setupShippingCondition(int price) {
        binding.txtShippingConditional.setText(
            String.format(resources.getString(format_shipping_conditional), StringUtil.toDigit(price)));
    }

    @Override public void showShippingCondition() {
        binding.txtShippingConditional.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingCondition() {
        binding.txtShippingConditional.setVisibility(View.GONE);
    }

    @Override public void scrollToProductInfo() {
        binding.nsvRoot.smoothScrollTo(0, (int) binding.webProductDetail.getY() - binding.layoutTabPanel.getHeight());
    }

    @Override public void scrollToReview() {
        binding.nsvRoot.smoothScrollTo(0, (int) binding.layoutReviewPanel.getY() - binding.layoutTabPanel.getHeight());
    }

    @Override public void scrollToQuestion() {
        binding.nsvRoot.smoothScrollTo(0,
            (int) binding.layoutQuestionPanel.getY() - binding.layoutTabPanel.getHeight());
    }

    @Override public void scrollToShippingNRefund() {
        binding.nsvRoot.smoothScrollTo(0,
            (int) binding.layoutShippingNRefundPanel.getY() - binding.layoutTabPanel.getHeight());
    }

    @Override public void setupProductDetail(String url) {
        binding.webProductDetail.loadUrl(url);
    }

    @Override public void setupReviewCount(String text) {
        binding.txtReviewCount.setText(text);
        getTabView(1).setupCount(text);
        binding.txtReviewMore.setText(String.format(resources.getString(format_review_more), text));
    }

    @Override public void showReviewContentsPanel() {
        binding.layoutReviewContentsPanel.setVisibility(View.VISIBLE);
    }

    @Override public void setupQuestionCount(String text) {
        binding.txtQuestionCount.setText(text);
        getTabView(2).setupCount(text);
    }

    @Override public void setupOptionSelector(int price, List<OptionColorDataModel> options) {
        binding.optionSelector.setupData(price, options);
        binding.optionSelector.setOnButtonClickListener(new ProductOptionSelector.OnButtonClickListener() {
            @Override public void onShoppingBagClick(List<ShoppingOptionDataModel> optionData) {
                presenter.onOptionSelectorShoppingBagClick(optionData);
            }

            @Override public void onBuyClick(List<ShoppingOptionDataModel> optionData) {
                presenter.onOptionSelectorBuyClick(optionData);
            }
        });
    }

    @Override public void showOptionSelector() {
        binding.optionSelector.show();
    }

    @Override public void hideOptionSelector() {
        binding.optionSelector.hide();
    }

    @Override public void setupInfoStyleNo(String text) {
        binding.txtInfoStyleNo.setText(text);
    }

    @Override public void setupInfoKcAuth(String text) {
        binding.txtInfoKcAuth.setText(text);
    }

    @Override public void setupInfoWeight(String text) {
        binding.txtInfoWeight.setText(text);
    }

    @Override public void setupInfoColor(String text) {
        binding.txtInfoColor.setText(text);
    }

    @Override public void setupInfoMaterial(String text) {
        binding.txtInfoMaterial.setText(text);
    }

    @Override public void setupInfoAge(String text) {
        binding.txtInfoAge.setText(text);
    }

    @Override public void setupInfoReleaseDate(String text) {
        binding.txtInfoReleaseDate.setText(text);
    }

    @Override public void setupInfoManufacturer(String text) {
        binding.txtInfoManufacturer.setText(text);
    }

    @Override public void setupInfoCountry(String text) {
        binding.txtInfoCountry.setText(text);
    }

    @Override public void setupInfoCaution(String text) {
        binding.txtInfoCaution.setText(text);
    }

    @Override public void setupInfoWarranty(String text) {
        binding.txtInfoWarranty.setText(text);
    }

    @Override public void setupInfoDamage(String text) {
        binding.txtInfoDamage.setText(text);
    }

    @Override public void setupInfoServiceCenter(String text) {
        binding.txtInfoServiceCenter.setText(text);
    }

    @Override public void expandInfoMorePanel() {
        binding.layoutInfoMorePanel.setVisibility(View.VISIBLE);
        binding.txtExpand.setText(str_collapse);
        binding.imgExpand.setImageDrawable(resources.getDrawable(img_arrow_up));
    }

    @Override public void collapseInfoMorePanel() {
        binding.layoutInfoMorePanel.setVisibility(View.GONE);
        binding.txtExpand.setText(str_expand);
        binding.imgExpand.setImageDrawable(resources.getDrawable(img_arrow_down));
    }

    @Override public void showDisplayLabel(String label) {
        binding.txtDisplayLabel.setText(label);
        binding.txtDisplayLabel.setVisibility(View.VISIBLE);
    }

    @Override public void disableBuyButton(String label) {
        binding.txtBuy.setText(label);
        binding.txtBuy.setBackgroundColor(resources.getColor(color_FFDBDBDB));
        binding.txtBuy.setEnabled(false);
    }

    @Override public void setupPriceOriginNoDisplayColor() {
        binding.txtPriceOrigin.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void setupDiscountPercentNoDisplayColor() {
        binding.txtDiscountPercent.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void setupPriceNoDisplayColor() {
        binding.txtPrice.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void checkScrap() {
        binding.imgScrap.setImageDrawable(resources.getDrawable(img_scrap_on));
    }

    @Override public void uncheckScrap() {
        binding.imgScrap.setImageDrawable(resources.getDrawable(img_scrap_off));
    }

    @Override public void hideScrap() {
        binding.imgScrap.setVisibility(View.GONE);
    }

    @Override public void navigateToProductInfo(int brand_id) {
        ProductInfoActivity.start(this, brand_id);
    }

    @Override public void navigateToBrandDetail(int brand_id) {
        BrandDetailActivity.start(this, brand_id);
    }

    @Override public void navigateToShoppingBag() {
        ShoppingBagActivity.start(this);
    }

    @Override public void showAddShoppingBagToast() {
        Toast.makeText(this, str_add_shoppingbag, Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToEventDetail() {
        EventDetailActivity.start(this, 6);
    }

    @Override public void navigateToOrder(List<ShoppingBrandDataModel> items) {
        App.getInstance().setOrderItem(items);
        OrderActivity.start(this, ProductDetailActivity.class.getSimpleName());
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void navigateToReview(int productNo) {
        ReviewActivity.start(this, productNo);
    }

    @Override public void navigateToQuestion(int productNo) {
        QuestionActivity.start(this, productNo);
    }

    @Override public void sendDynamicLink(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @OnClick(R.id.layout_expand_panel)
    void onExpandClick() {
        presenter.onExpandClick();
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        presenter.onBrandClick();
    }

    @OnClick({ R.id.layout_review_panel, R.id.txt_review_more })
    void onReviewMoreClick() {
        presenter.onReviewMoreClick();
    }

    @OnClick(R.id.layout_question_panel)
    void onQuestionClick() {
        presenter.onQuestionClick();
    }

    @OnClick(R.id.layout_shipping_n_refund_panel)
    void onShippingNRefundClick() {
        presenter.onShippingNRefundClick();
    }

    @OnClick(R.id.img_share)
    void onShareClick() {
        presenter.onShareClick();
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {
        presenter.onScrapClick();
    }

    @OnClick(R.id.txt_buy)
    void onBuyClick() {
        presenter.onBuyClick();
    }
}
