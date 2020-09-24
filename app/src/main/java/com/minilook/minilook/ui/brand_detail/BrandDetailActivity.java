package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
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
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailSortAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailStyleAdapter;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.brand_info.BrandInfoActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandDetailActivity extends BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int brand_id) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.putExtra("brand_id", brand_id);
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
    @BindView(R.id.layout_product_panel) LinearLayout productPanel;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    @BindView(R.id.txt_sort) TextView sortTextView;
    @BindView(R.id.rcv_sort) RecyclerView sortRecyclerView;
    @BindView(R.id.layout_header_panel) LinearLayout headerPanel;
    @BindView(R.id.txt_header_sort) TextView headerSortTextView;
    @BindView(R.id.rcv_header_sort) RecyclerView headerSortRecyclerView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;
    ;
    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;
    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;

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
            .brand_id(getIntent().getIntExtra("brand_id", -1))
            .styleAdapter(styleAdapter)
            .sortAdapter(sortAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupScrollView() {
        rootScrollView.setOnScrollChangeListener(
            (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY > productPanel.getY()) {
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
            headerPanel.setVisibility(View.VISIBLE);
            if (sortRecyclerView.getVisibility() == View.VISIBLE) {
                sortRecyclerView.setVisibility(View.GONE);
                headerSortRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            headerPanel.setVisibility(View.GONE);
            if (headerSortRecyclerView.getVisibility() == View.VISIBLE) {
                sortRecyclerView.setVisibility(View.VISIBLE);
                headerSortRecyclerView.setVisibility(View.GONE);
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
        headerSortRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerSortRecyclerView.setAdapter(sortAdapter);
        itemDecoration.addTo(sortRecyclerView);
        itemDecoration.addTo(headerSortRecyclerView);
    }

    @Override public void sortRefresh() {
        sortAdapterView.refresh();
    }

    @Override public void setupSortText(String text) {
        sortTextView.setText(text);
        headerSortTextView.setText(text);
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

    @Override public void showSortPanel() {
        if (headerPanel.getVisibility() == View.VISIBLE) {
            headerSortRecyclerView.setVisibility(View.VISIBLE);
        } else {
            sortRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override public void hideSortPanel() {
        if (headerPanel.getVisibility() == View.VISIBLE) {
            headerSortRecyclerView.setVisibility(View.GONE);
        } else {
            sortRecyclerView.setVisibility(View.GONE);
        }
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

    @Override public void setupName(String text) {
        nameTextView.setText(text);
    }

    @Override public void setupTag(String text) {
        tagTextView.setText(text);
    }

    @Override public void setupDesc(String text) {
        descTextView.setText(text);
    }

    @Override public void checkScrap() {
        scrapImageView.setImageDrawable(img_scrap_on);
    }

    @Override public void uncheckScrap() {
        scrapImageView.setImageDrawable(img_scrap_off);
    }

    @Override public void scrollToTop() {
        if (rootScrollView.getScrollY() > productPanel.getY()) rootScrollView.setScrollY((int) productPanel.getY());
    }

    @Override public void navigateToBrandInfo(int brand_id) {
        BrandInfoActivity.start(this, brand_id);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @OnClick({ R.id.img_scrap, R.id.txt_scrap })
    void onScrapClick() {
        presenter.onScrapClick();
    }

    @OnClick(R.id.layout_brand_info_panel)
    void onBrandInfoClick() {
        presenter.onBrandInfoClick();
    }

    @OnClick({ R.id.layout_sort_panel, R.id.layout_header_sort_panel })
    void onSortClick() {
        presenter.onSortClick();
    }
}
