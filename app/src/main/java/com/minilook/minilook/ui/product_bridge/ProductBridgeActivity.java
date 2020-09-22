package com.minilook.minilook.ui.product_bridge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.brand_detail.adapter.BrandDetailSortAdapter;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;
import com.minilook.minilook.ui.search_filter.SearchFilterActivity;
import com.minilook.minilook.util.DimenUtil;
import java.util.List;
import java.util.Objects;

public class ProductBridgeActivity extends BaseActivity implements ProductBridgePresenter.View {

    public static void start(Context context, SearchOptionDataModel model) {
        Intent intent = new Intent(context, ProductBridgeActivity.class);
        intent.putExtra("options", model);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.txt_total) TextView totalTextView;
    @BindView(R.id.txt_sort) TextView sortTextView;
    @BindView(R.id.rcv_sort) RecyclerView sortRecyclerView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private ProductBridgePresenter presenter;
    private BrandDetailSortAdapter sortAdapter = new BrandDetailSortAdapter();
    private BaseAdapterDataView<CodeDataModel> sortAdapterView = sortAdapter;
    private ProductAdapter productAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_product_bridge;
    }

    @Override protected void createPresenter() {
        presenter = new ProductBridgePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductBridgeArguments provideArguments() {
        return ProductBridgeArguments.builder()
            .view(this)
            .options((SearchOptionDataModel) getIntent().getSerializableExtra("options"))
            .sortAdapter(sortAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupTitle(String title) {
        titleBar.setTitle(title);
    }

    @Override public void setupTabItems(List<CategoryDataModel> categories) {
        for (CategoryDataModel category : categories) {
            TabView tabView = TabView.builder()
                .context(this)
                .name(category.getName())
                .unselectedTextColor(color_FF424242)
                .selectedTextColor(color_FF8140E5)
                .unselectedTextFont(font_bold)
                .selectedTextFont(font_bold)
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
            }
        });
    }

    public TabView getTabView(TabLayout.Tab tab) {
        return (TabView) tab.getCustomView();
    }

    public TabView getTabView(TabLayout tabLayout, int position) {
        return (TabView) Objects.requireNonNull(tabLayout.getTabAt(position)).getCustomView();
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

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        productRecyclerView.setAdapter(productAdapter);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(productRecyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        productRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
    }

    @Override public void productRefresh(int start, int rows) {
        productAdapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        emptyPanel.setVisibility(View.GONE);
    }

    @Override public void navigateToSearchFilter() {
        SearchFilterActivity.start(this);
    }

    @OnClick(R.id.layout_sort_panel)
    void onSortClick() {
        presenter.onSortClick();
    }

    @OnClick(R.id.txt_filter)
    void onFilterClick() {
        presenter.onFilterClick();
    }
}
