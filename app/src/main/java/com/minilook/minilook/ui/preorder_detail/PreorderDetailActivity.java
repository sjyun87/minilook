package com.minilook.minilook.ui.preorder_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.OptionDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.option_selector.OptionSelector;
import com.minilook.minilook.ui.order.OrderActivity;
import com.minilook.minilook.ui.preorder_detail.adapter.PreorderDetailImageAdapter;
import com.minilook.minilook.ui.preorder_detail.adapter.PreorderDetailProductAdapter;
import com.minilook.minilook.ui.preorder_detail.di.PreorderDetailArguments;
import com.minilook.minilook.ui.preorder_info.PreorderInfoActivity;
import com.minilook.minilook.ui.preorder_product_detail.PreorderProductDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.product_detail.widget.ProductTabView;
import com.minilook.minilook.util.DynamicLinkManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.List;
import java.util.Objects;
import me.didik.component.StickyNestedScrollView;

public class PreorderDetailActivity extends _BaseActivity implements PreorderDetailPresenter.View {

    public static void start(Context context, int preorderNo) {
        Intent intent = new Intent(context, PreorderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("preorderNo", preorderNo);
        context.startActivity(intent);
    }

    @BindView(R.id.nsv_root) StickyNestedScrollView scrollView;
    @BindView(R.id.vp_preorder_image) ViewPager2 preorderImageViewPager;
    @BindView(R.id.indicator) DotsIndicator indicator;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_preorder_title) TextView preorderTitleTextView;
    @BindView(R.id.txt_state) TextView stateTextView;
    @BindView(R.id.layout_remain_panel) LinearLayout remainPanel;
    @BindView(R.id.txt_remain_date) TextView remainDateTextView;
    @BindView(R.id.txt_term_date) TextView termDateTextView;
    @BindView(R.id.txt_preorder_close) TextView closeTextView;
    @BindView(R.id.txt_delivery_date) TextView deliveryDateTextView;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.img_detail) ImageView detailImageView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;
    @BindView(R.id.layout_shipping_n_refund_panel) LinearLayout shippingNRefundPanel;
    @BindView(R.id.txt_buy) TextView buyTextView;
    @BindView(R.id.option_selector) OptionSelector optionSelector;

    @BindString(R.string.preorder_detail_remain_date_unit) String format_remain_date;
    @BindString(R.string.dialog_error_title) String str_error_msg;

    @BindArray(R.array.tab_preorder_detail) String[] tabNames;

    @BindDrawable(R.drawable.bg_round_border_purple) Drawable bg_label_purple;
    @BindDrawable(R.drawable.bg_round_border_gray) Drawable bg_label_gray;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    private PreorderDetailPresenter presenter;
    private PreorderDetailImageAdapter imageAdapter = new PreorderDetailImageAdapter();
    private BaseAdapterDataView<String> imageAdapterView = imageAdapter;
    private PreorderDetailProductAdapter productAdapter = new PreorderDetailProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_preorder_detail;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderDetailArguments provideArguments() {
        return PreorderDetailArguments.builder()
            .view(this)
            .preorderNo(getIntent().getIntExtra("preorderNo", -1))
            .imageAdapter(imageAdapter)
            .productAdapter(productAdapter)
            .dynamicLinkManager(new DynamicLinkManager(this))
            .build();
    }

    @Override public void setupViewPager() {
        preorderImageViewPager.setAdapter(imageAdapter);
        indicator.setViewPager2(preorderImageViewPager);
    }

    @Override public void imageRefresh() {
        imageAdapterView.refresh();
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

    @Override public void setDetailImage(String url) {
        Glide.with(this)
            .load(url)
            .apply(new RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL))
            .into(detailImageView);
    }

    @Override public void setupRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
    }

    public ProductTabView getTabView(TabLayout.Tab tab) {
        return (ProductTabView) tab.getCustomView();
    }

    public ProductTabView getTabView(int position) {
        return (ProductTabView) Objects.requireNonNull(tabLayout.getTabAt(position)).getCustomView();
    }

    @Override public void setBrandName(String name) {
        brandNameTextView.setText(name);
    }

    @Override public void setTitle(String title) {
        preorderTitleTextView.setText(title);
    }

    @Override public void setRemainDate(int count) {
        remainDateTextView.setText(String.format(format_remain_date, count));
    }

    @Override public void setTermDate(String date) {
        termDateTextView.setText(date);
    }

    @Override public void setDeliveryDate(String date) {
        deliveryDateTextView.setText(date);
    }

    @Override public void setEnableLabelBackground() {
        stateTextView.setBackground(bg_label_purple);
        stateTextView.setTextColor(color_FF8140E5);
    }

    @Override public void setDisableLabelBackground() {
        stateTextView.setBackground(bg_label_gray);
        stateTextView.setTextColor(color_FFA9A9A9);
    }

    @Override public void setLabel(String label) {
        stateTextView.setText(label);
    }

    @Override public void enableBuyButton() {
        buyTextView.setEnabled(true);
        buyTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableBuyButton(String name) {
        buyTextView.setEnabled(false);
        buyTextView.setBackgroundColor(color_FFDBDBDB);
        buyTextView.setText(name);
    }

    @Override public void showCloseTextView() {
        remainPanel.setVisibility(View.GONE);
        closeTextView.setVisibility(View.VISIBLE);
    }

    @Override public void scrollToPreorderInfo() {
        scrollView.smoothScrollTo(0, (int) detailImageView.getY() - tabLayout.getHeight());
    }

    @Override public void scrollToProduct() {
        scrollView.smoothScrollTo(0, (int) productRecyclerView.getY() - tabLayout.getHeight());
    }

    @Override public void scrollToShippingNRefund() {
        scrollView.smoothScrollTo(0, (int) shippingNRefundPanel.getY() - tabLayout.getHeight());
    }

    @Override public void navigateToPreorderProductDetail(String title, int preorderNo, int productNo) {
        PreorderProductDetailActivity.start(this, title, preorderNo, productNo);
    }

    @Override public void setupOptionSelector(OptionDataModel options) {
        optionSelector.setupData(options);
        optionSelector.setOnButtonClickListener(
            shoppingProductData -> presenter.onOptionSelectorBuyClick(shoppingProductData));
    }

    @Override public void showOptionSelector() {
        optionSelector.show();
    }

    @Override public void hideOptionSelector() {
        optionSelector.hide();
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void navigateToOrder(List<ShoppingBrandDataModel> items) {
        App.getInstance().setOrderItem(items);
        OrderActivity.start(this, ProductDetailActivity.class.getSimpleName());
    }

    @Override public void sendLink(String shareLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareLink);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void showErrorMessage() {
        Toast.makeText(this, str_error_msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({ R.id.img_info, R.id.layout_shipping_n_refund_panel })
    void onInfoClick() {
        PreorderInfoActivity.start(this);
    }

    @OnClick(R.id.txt_buy)
    void onBuyClick() {
        presenter.onBuyClick();
    }

    @OnClick(R.id.img_share)
    void onShareClick() {
        presenter.onShareClick();
    }
}
