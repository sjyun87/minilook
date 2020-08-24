package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailSortAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailStyleAdapter;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.brand_info.BrandInfoActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandDetailActivity extends BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int brand_id) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brand_id", brand_id);
        context.startActivity(intent);
    }

    @BindView(R.id.nsv_contents) NestedScrollView rootScrollView;
    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_scrap) TextView scrapCountTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.layout_product_panel) LinearLayout productPanel;
    @BindView(R.id.txt_sort) TextView sortTextView;
    @BindView(R.id.rcv_sort) RecyclerView sortRecyclerView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    @BindView(R.id.layout_header_panel) LinearLayout headerPanel;
    @BindView(R.id.rcv_header_sort) RecyclerView headerSortRecyclerView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;
    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private BrandDetailPresenter presenter;
    private BrandDetailStyleAdapter styleAdapter = new BrandDetailStyleAdapter();
    private BaseAdapterDataView<String> styleAdapterView = styleAdapter;
    private BrandDetailSortAdapter sortAdapter = new BrandDetailSortAdapter();
    private BaseAdapterDataView<SortDataModel> sortAdapterView = sortAdapter;
    private ProductAdapter productAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

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

                if (scrollY == ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) - 300)) {
                    presenter.onLoadMore();
                }
            });
    }

    @Override public void setupStyleRecyclerView() {
        styleRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        styleRecyclerView.setAdapter(styleAdapter);
        DividerDecoration.builder(this)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(styleRecyclerView);
    }

    @Override public void styleRefresh() {
        styleAdapterView.refresh();
    }

    @Override public void setupSortRecyclerView() {
        sortRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sortAdapter.setOnSortSelectListener(presenter::onSortSelected);
        sortRecyclerView.setAdapter(sortAdapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(color_FFF5F5F5)
            .build()
            .addTo(sortRecyclerView);

        headerSortRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sortAdapter.setOnSortSelectListener(presenter::onSortSelected);
        headerSortRecyclerView.setAdapter(sortAdapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(color_FFF5F5F5)
            .build()
            .addTo(headerSortRecyclerView);
    }

    @Override public void sortRefresh() {
        sortAdapterView.refresh();
    }

    @Override public void setupSortText(String text) {
        sortTextView.setText(text);
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
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
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(thumbImageView);
    }

    @Override public void setupLogo(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(this, 1), color_FFDBDBDB)))
            .into(logoImageView);
    }

    @Override public void setupScrapCount(String text) {
        scrapCountTextView.setText(text);
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

    @Override public void navigateToBrandInfo(int brand_id) {
        BrandInfoActivity.start(this, brand_id);
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
