package com.minilook.minilook.ui.market;

import android.view.View;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.databinding.FragmentMarketBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.market.adapter.MarketModuleAdapter;
import com.minilook.minilook.ui.market.di.MarketArguments;

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

    @Override public void setRefreshing(boolean flag) {
        if (binding.layoutSwipeRefresh.isRefreshing() != flag) binding.layoutSwipeRefresh.setRefreshing(flag);
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

    @Override public void attachedToWindow() {
        adapter.onAttach();
    }

    @Override public void detachToWindow() {
        adapter.onDetach();
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(getActivity());
    }
}
