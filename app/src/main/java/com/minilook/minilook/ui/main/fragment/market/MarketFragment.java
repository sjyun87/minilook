package com.minilook.minilook.ui.main.fragment.market;

import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.market.adapter.MarketModuleAdapter;
import com.minilook.minilook.ui.main.fragment.market.di.MarketArguments;

import butterknife.BindDrawable;
import butterknife.BindView;

public class MarketFragment extends BaseFragment implements MarketPresenter.View {

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @BindView(R.id.rcv_market) RecyclerView marketRecyclerView;

    @BindDrawable(R.drawable.divider_market) Drawable divider;

    private MarketPresenter presenter;
    private MarketModuleAdapter adapter = new MarketModuleAdapter();
    private BaseAdapterDataView adapterView = adapter;

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

    @Override public void setupRecyclerView() {
        marketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        marketRecyclerView.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .drawable(divider)
            .build()
            .addTo(marketRecyclerView);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
