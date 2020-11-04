package com.minilook.minilook.ui.market;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.network.market.MarketRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.market.di.MarketArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketPresenterImpl extends BasePresenterImpl implements MarketPresenter {

    private final View view;
    private final BaseAdapterDataModel<MarketDataModel> adapter;
    private final MarketRequest marketRequest;

    private Gson gson = new Gson();

    public MarketPresenterImpl(MarketArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        marketRequest = new MarketRequest();
    }

    @Override public void onCreate() {
        view.setupRefreshLayout();
        view.setupRecyclerView();
        reqMarketModule();
    }

    @Override public void onRefresh() {
        reqMarketModule();
    }

    private void reqMarketModule() {
        addDisposable(marketRequest.getMarketModules()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<MarketDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<MarketDataModel>>() {
                }.getType()))
            .subscribe(this::resMarketModules, Timber::e));
    }

    private void resMarketModules(@NonNull List<MarketDataModel> data) {
        List<MarketDataModel> item = new ArrayList<>();
        item.add(data.get(0));
        item.add(data.get(1));
        item.add(data.get(3));
        item.add(data.get(6));
        item.add(data.get(7));

        adapter.set(item);
        view.refresh();
        view.setRefreshing();
    }
}