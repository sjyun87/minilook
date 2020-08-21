package com.minilook.minilook.ui.brand_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailCategoryAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailSortAdapter;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailStyleAdapter;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandDetailActivity extends BaseActivity implements BrandDetailPresenter.View {

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, BrandDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("brand_id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_scrap) TextView scrapCountTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.rcv_pick) RecyclerView pickRecyclerView;
    @BindView(R.id.rcv_category) RecyclerView categoryRecyclerView;
    @BindView(R.id.txt_sort) TextView sortTextView;
    @BindView(R.id.rcv_sort) RecyclerView sortRecyclerView;
    @BindView(R.id.rcv_goods) RecyclerView productRecyclerView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private BrandDetailPresenter presenter;
    private BrandDetailStyleAdapter styleAdapter = new BrandDetailStyleAdapter();
    private BaseAdapterDataView<String> styleAdapterView = styleAdapter;
    private ProductAdapter pickAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> pickAdapterView = pickAdapter;
    private BrandDetailCategoryAdapter categoryAdapter = new BrandDetailCategoryAdapter();
    private BaseAdapterDataView<CategoryDataModel> categoryAdapterView = categoryAdapter;
    private BrandDetailSortAdapter sortAdapter = new BrandDetailSortAdapter();
    private BaseAdapterDataView<SortDataModel> sortAdapterView = sortAdapter;

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
            .id(getIntent().getIntExtra("brand_id", -1))
            .styleAdapter(styleAdapter)
            .pickAdapter(pickAdapter)
            .categoryAdapter(categoryAdapter)
            .sortAdapter(sortAdapter)
            .build();
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

    @Override public void setupPickRecyclerView() {
        //pickRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //pickAdapter.setViewType(ProductAdapter.VIEW_TYPE_NO_BRAND);
        //pickRecyclerView.setAdapter(pickAdapter);
        //DividerDecoration.builder(this)
        //    .size(dp_2)
        //    .asSpace()
        //    .build()
        //    .addTo(pickRecyclerView);
    }

    @Override public void pickRefresh() {
        pickAdapterView.refresh();
    }

    @Override public void setupCategoryRecyclerView() {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categoryAdapter.setOnItemClickListener(presenter::onCategoryItemClick);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override public void categoryRefresh() {
        categoryAdapterView.refresh();
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
    }

    @Override public void sortRefresh() {
        sortAdapterView.refresh();
    }

    @Override public void setupSortText(String text) {
        sortTextView.setText(text);
    }

    @Override public void showSortPanel() {
        sortRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override public void hideSortPanel() {
        sortRecyclerView.setVisibility(View.GONE);
    }

    @Override public void setupThumb(String url) {
        Glide.with(this)
            .load(url)
            .into(thumbImageView);
    }

    @Override public void setupLogo(String url) {
        Glide.with(this)
            .load(url)
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

    @OnClick(R.id.layout_sort_panel)
    void onSortClick() {
        presenter.onSortClick();
    }
}
