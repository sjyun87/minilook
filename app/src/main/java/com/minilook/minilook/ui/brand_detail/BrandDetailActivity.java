package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.BaseDividerItemDecoration;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailSortAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailStyleAdapter;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.brand_info.BrandInfoActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandDetailActivity extends _BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int brandNo) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.putExtra("brandNo", brandNo);
        context.startActivity(intent);
    }

    @BindView(R.id.nsv_contents) NestedScrollView rootScrollView;
    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_scrap) TextView scrapCountTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    @BindView(R.id.layout_header_panel) LinearLayout headerPanel;
    @BindView(R.id.txt_sort) TextView sortTextView;
    @BindView(R.id.rcv_sort) RecyclerView sortRecyclerView;
    @BindView(R.id.layout_sticky_panel) LinearLayout stickyPanel;
    @BindView(R.id.txt_sticky_sort) TextView stickySortTextView;
    @BindView(R.id.rcv_sticky_sort) RecyclerView stickySortRecyclerView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;
    @BindDrawable(R.drawable.ph_circle) Drawable img_placeholder_logo;

    @BindString(R.string.dialog_error_title) String str_error_msg;

    @BindDimen(R.dimen.dp_2) int dp_2;
    @BindDimen(R.dimen.dp_50) int dp_50;
    @BindDimen(R.dimen.dp_150) int dp_150;

    private BrandDetailPresenter presenter;
    private BrandDetailStyleAdapter styleAdapter = new BrandDetailStyleAdapter();
    private BaseAdapterDataView<String> styleAdapterView = styleAdapter;
    private BrandDetailSortAdapter sortAdapter = new BrandDetailSortAdapter();
    private BaseAdapterDataView<CodeDataModel> sortAdapterView = sortAdapter;
    private ProductAdapter productAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

    private boolean isLoading = false;

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
            .brandNo(getIntent().getIntExtra("brandNo", -1))
            .styleAdapter(styleAdapter)
            .sortAdapter(sortAdapter)
            .productAdapter(productAdapter)
            .dynamicLinkManager(new DynamicLinkManager(this))
            .build();
    }

    @Override public void setupScrollView() {
        rootScrollView.setOnScrollChangeListener(
            (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY > headerPanel.getY()) {
                    handleHeaderPanel(true);
                } else {
                    handleHeaderPanel(false);
                }

                if (scrollY > ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) - (dp_150 * 3))) {
                    if (!isLoading) {
                        isLoading = true;
                        presenter.onLoadMore();
                    }
                }
            });
    }

    private void handleHeaderPanel(boolean isHeaderVisible) {
        if (isHeaderVisible) {
            stickyPanel.setVisibility(View.VISIBLE);
            if (sortRecyclerView.getVisibility() == View.VISIBLE) {
                sortRecyclerView.setVisibility(View.GONE);
                stickySortRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            stickyPanel.setVisibility(View.GONE);
            if (stickySortRecyclerView.getVisibility() == View.VISIBLE) {
                sortRecyclerView.setVisibility(View.VISIBLE);
                stickySortRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override public void setupStyleRecyclerView() {
        styleRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        styleRecyclerView.setAdapter(styleAdapter);
        DividerDecoration.builder(this)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(styleRecyclerView);
        ViewCompat.setNestedScrollingEnabled(styleRecyclerView, false);
    }

    @Override public void styleRefresh() {
        styleAdapterView.refresh();
    }

    @Override public void setupSortRecyclerView() {
        sortAdapter.setOnSortSelectListener(presenter::onSortSelected);
        BaseDividerItemDecoration itemDecoration =
            DividerDecoration.builder(this)
                .size(DimenUtil.dpToPx(this, 1))
                .color(color_FFF5F5F5)
                .build();

        sortRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sortRecyclerView.setAdapter(sortAdapter);
        stickySortRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stickySortRecyclerView.setAdapter(sortAdapter);
        itemDecoration.addTo(sortRecyclerView);
        itemDecoration.addTo(stickySortRecyclerView);
    }

    @Override public void sortRefresh() {
        sortAdapterView.refresh();
    }

    @Override public void setupSortText(String name) {
        sortTextView.setText(name);
        stickySortTextView.setText(name);
    }

    @Override public void showSortPanel() {
        if (stickyPanel.getVisibility() == View.VISIBLE) {
            stickySortRecyclerView.setVisibility(View.VISIBLE);
        } else {
            sortRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override public void hideSortPanel() {
        if (stickyPanel.getVisibility() == View.VISIBLE) {
            stickySortRecyclerView.setVisibility(View.GONE);
        } else {
            sortRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        productAdapter.setShowBrand(false);
        productRecyclerView.setAdapter(productAdapter);
        ViewCompat.setNestedScrollingEnabled(productRecyclerView, false);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
        isLoading = false;
    }

    @Override public void productRefresh(int start, int row) {
        productAdapterView.refresh(start, row);
        isLoading = false;
    }

    @Override public void setupThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(img_placeholder_wide)
            .error(img_placeholder_wide)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);
    }

    @Override public void setupLogo(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .transition(new DrawableTransitionOptions().crossFade())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(this, 1), color_FFDBDBDB)))
            .into(logoImageView);
    }

    @Override public void setupScrapCount(int count) {
        scrapCountTextView.setText(StringUtil.toDigit(count));
    }

    @Override public void setupName(String name) {
        nameTextView.setText(name);
    }

    @Override public void setupTag(String tag) {
        tagTextView.setText(tag.replace(",", " "));
    }

    @Override public void setupDesc(String desc) {
        descTextView.setText(desc);
    }

    @Override public void scrapOn() {
        scrapImageView.setImageDrawable(img_scrap_on);
    }

    @Override public void scrapOff() {
        scrapImageView.setImageDrawable(img_scrap_off);
    }

    @Override public void scrollToTop() {
        if (rootScrollView.getScrollY() > headerPanel.getY()) rootScrollView.setScrollY((int) headerPanel.getY());
    }

    @Override public void navigateToBrandInfo(int brandNo) {
        BrandInfoActivity.start(this, brandNo);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
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

    @OnClick({ R.id.img_scrap, R.id.txt_scrap })
    void onScrapClick() {
        presenter.onScrapClick();
    }

    @OnClick(R.id.layout_brand_info_panel)
    void onBrandInfoClick() {
        presenter.onBrandInfoClick();
    }

    @OnClick({ R.id.layout_sort_panel, R.id.layout_sticky_sort_panel })
    void onSortClick() {
        presenter.onSortClick();
    }

    @OnClick(R.id.img_share)
    void onShareClick() {
        presenter.onShareClick();
    }
}
