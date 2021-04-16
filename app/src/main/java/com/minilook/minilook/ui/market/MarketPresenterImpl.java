package com.minilook.minilook.ui.market;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.MarketModuleType;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.network.market.MarketRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.market.di.MarketArguments;
import com.minilook.minilook.util.TrackingUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketPresenterImpl extends BasePresenterImpl implements MarketPresenter {

    private final View view;
    private final BaseAdapterDataModel<MarketDataModel> adapter;
    private final MarketRequest marketRequest;
    private final Gson gson;

    public MarketPresenterImpl(MarketArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        marketRequest = new MarketRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        view.setupRefreshLayout();
        view.setupRecyclerView();

        getMarketModules();
    }

    @Override public void onResume() {
        view.attachedToWindow();
        TrackingUtil.pageTracking("마켓페이지", MarketFragment.class.getSimpleName());
    }

    @Override public void onPause() {
        view.detachToWindow();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onRefresh() {
        getMarketModules();
    }

    private void getMarketModules() {
        addDisposable(marketRequest.getMarketModules()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<MarketDataModel>>)
                data -> checkValidData(gson.fromJson(data.getData(), new TypeToken<ArrayList<MarketDataModel>>() {
                }.getType())))
            .subscribe(this::onResMarketModules, Timber::e));
    }

    private List<MarketDataModel> checkValidData(List<MarketDataModel> data) {
        return Observable.fromIterable(data)
            .filter(model -> MarketModuleType.toModuleType(model.getType()) != -1)
            .filter(model -> {
                JsonElement data1 = model.getData();
                if (data1.isJsonArray()) {
                    return data1.getAsJsonArray().size() != 0;
                } else {
                    return true;
                }
            })
            .map(model -> {
                model.setRefreshing(true);
                return model;
            })
            .toList()
            .blockingGet();
    }

    private void onResMarketModules(List<MarketDataModel> data) {
        adapter.set(data);
        view.refresh();
        view.setRefreshing(false);
    }
}