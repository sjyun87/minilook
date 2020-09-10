package com.minilook.minilook.ui.market;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.network.market.MarketRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.MarketModuleCode;
import com.minilook.minilook.data.type.MarketModuleType;
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
        adapter.set(parseToModuleType(data));
        view.refresh();
        view.setRefreshing();
    }

    private List<MarketDataModel> parseToModuleType(List<MarketDataModel> data) {
        List<MarketDataModel> items = new ArrayList<>();
        for (MarketDataModel model : data) {
            if (model.getModule().equals(MarketModuleCode.COMMERCIAL.name())) {
                model.setType(MarketModuleType.TYPE_COMMERCIAL.getValue());
                items.add(model);
            } else if (model.getModule().equals(MarketModuleCode.TODAY.name())) {
                model.setType(MarketModuleType.TYPE_LIMITED.getValue());
                items.add(model);
            } else if (model.getModule().equals(MarketModuleCode.NEW.name())) {
                model.setType(MarketModuleType.TYPE_NEW_ARRIVALS.getValue());
                items.add(model);
            } else if (model.getModule().equals(MarketModuleCode.BRAND.name())) {
                model.setType(MarketModuleType.TYPE_BRAND.getValue());
                items.add(model);
            } else if (model.getModule().equals(MarketModuleCode.CATEGORY.name())) {
                model.setType(MarketModuleType.TYPE_FILTER.getValue());
                items.add(model);
            } else if (model.getModule().equals(MarketModuleCode.RECOMMEND.name())) {
                int visibleCount = model.getVisible_cnt();
                if (visibleCount == 4) {
                    model.setType(MarketModuleType.TYPE_RECOMMEND_4.getValue());
                    items.add(model);
                } else if (visibleCount == 5) {
                    model.setType(MarketModuleType.TYPE_RECOMMEND_5.getValue());
                    items.add(model);
                } else if (visibleCount == 6) {
                    model.setType(MarketModuleType.TYPE_RECOMMEND_6.getValue());
                    items.add(model);
                } else if (visibleCount == 9) {
                    model.setType(MarketModuleType.TYPE_RECOMMEND_9.getValue());
                    items.add(model);
                }
            }
        }
        return items;
    }
}