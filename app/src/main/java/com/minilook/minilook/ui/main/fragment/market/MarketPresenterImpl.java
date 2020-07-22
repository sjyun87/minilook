package com.minilook.minilook.ui.main.fragment.market;

import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.network.market.MarketRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.market.di.MarketArguments;
import java.util.List;
import timber.log.Timber;

public class MarketPresenterImpl extends BasePresenterImpl implements MarketPresenter {

    private final View view;
    private final BaseAdapterDataModel<MarketDataModel> adapter;
    private final MarketRequest marketRequest;

    public MarketPresenterImpl(MarketArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        marketRequest = new MarketRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        reqMarketModule();
    }

    private void reqMarketModule() {
        addDisposable(marketRequest.getMarketModules()
            .compose(Transformer.applySchedulers())
            .subscribe(this::resMarketModules, Timber::e));
    }

    private void resMarketModules(List<MarketDataModel> data) {
        // 임시
        for (MarketDataModel model : data) {
            model.setType(model.getModule_type());
        }

        adapter.set(data.subList(0, 2));
        view.refresh();
    }
}