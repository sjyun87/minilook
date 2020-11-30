package com.minilook.minilook.ui.market;

import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.databinding.ActivitySplashBinding;
import com.minilook.minilook.databinding.FragmentMarketBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base._BaseFragment;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.market.adapter.MarketModuleAdapter;
import com.minilook.minilook.ui.market.di.MarketArguments;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class MarketFragment extends BaseFragment implements MarketPresenter.View {

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @DimenRes int dp_10 = R.dimen.dp_10;

    private FragmentMarketBinding binding;
    private MarketPresenter presenter;

    private final MarketModuleAdapter adapter = new MarketModuleAdapter();
    private final BaseAdapterDataView<MarketDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new MarketPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private MarketArguments provideArguments() {
        return MarketArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRefreshLayout() {
        binding.layoutSwipeRefresh.setOnRefreshListener(presenter::onRefresh);
    }

    @Override public void setRefreshing() {
        if (binding.layoutSwipeRefresh.isRefreshing()) binding.layoutSwipeRefresh.setRefreshing(false);
    }

    @Override public void setupRecyclerView() {
        binding.rcvMarket.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvMarket.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(getResources().getDimensionPixelSize(dp_10))
            .asSpace()
            .build()
            .addTo(binding.rcvMarket);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(getActivity());
    }

    @Override public void navigateToProductBridge(SearchOptionDataModel model) {
        ProductBridgeActivity.start(getContext(), model);
    }

    @Override public void navigateToPromotionDetail(int promotionNo) {
        PromotionDetailActivity.start(getContext(), promotionNo);
    }
}
