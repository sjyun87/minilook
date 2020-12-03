package com.minilook.minilook.ui.market;

import com.google.gson.Gson;
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
import com.minilook.minilook.util.TrackingManager;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
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

    @Override public void onCreate() {
        view.setupRefreshLayout();
        view.setupRecyclerView();

        getMarketModules();
    }

    @Override public void onResume() {
        TrackingManager.pageTracking("마켓페이지", MarketFragment.class.getSimpleName());
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
            .map(new Function<MarketDataModel, MarketDataModel>() {
                @Override public MarketDataModel apply(MarketDataModel model) throws Throwable {
                    model.setRefreshing(true);
                    return model;
                }
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