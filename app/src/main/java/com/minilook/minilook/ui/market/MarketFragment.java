package com.minilook.minilook.ui.market;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseFragment;
import com.minilook.minilook.ui.market.adapter.MarketModuleAdapter;
import com.minilook.minilook.ui.market.di.MarketArguments;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class MarketFragment extends _BaseFragment implements MarketPresenter.View {

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @BindView(R.id.layout_swipe_refresh) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rcv_market) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_10) int dp_10;

    private MarketPresenter presenter;
    private MarketModuleAdapter adapter = new MarketModuleAdapter();
    private BaseAdapterDataView<MarketDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_market;
    }

    @Override protected void createPresenter() {
        presenter = new MarketPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private MarketArguments provideArguments() {
        return MarketArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRefreshLayout() {
        refreshLayout.setOnRefreshListener(presenter::onRefresh);
    }

    @Override public void setRefreshing() {
        if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(dp_10)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void navigateToProductBridge(SearchOptionDataModel model) {
        ProductBridgeActivity.start(getContext(), model);
    }

    @Override public void navigateToPromotionDetail(int promotionNo) {
        PromotionDetailActivity.start(getContext(), promotionNo);
    }
}
