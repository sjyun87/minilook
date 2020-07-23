package com.minilook.minilook.ui.main.fragment.market;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.market.adapter.MarketModuleAdapter;
import com.minilook.minilook.ui.main.fragment.market.di.MarketArguments;

public class MarketFragment extends BaseFragment implements MarketPresenter.View {

    public static MarketFragment newInstance() {
        return new MarketFragment();
    }

    @BindView(R.id.rcv_market) RecyclerView recyclerView;

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

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
