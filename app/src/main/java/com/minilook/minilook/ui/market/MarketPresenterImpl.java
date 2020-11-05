package com.minilook.minilook.ui.market;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.code.MarketModuleType;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.market.MarketRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.market.di.MarketArguments;
import com.minilook.minilook.ui.market.viewholder.category.viewholder.MarketCategoryItemVH;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketPresenterImpl extends BasePresenterImpl implements MarketPresenter {

    private static final String SIZE_TYPE_BABY = "베이비";
    private static final String SIZE_TYPE_SHOES = "신발";
    private static final String SIZE_TYPE_ACCESSORIES = "가방/잡화";

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
        toRxObservable();
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
        adapter.set(checkData(data));
        view.refresh();
        view.setRefreshing();
    }

    private List<MarketDataModel> checkData(List<MarketDataModel> data) {
        return Observable.fromIterable(data)
            .filter(model -> MarketModuleType.toModuleType(model.getType()) != -1)
            .toList()
            .blockingGet();
    }

    private void navigateToProductBridge(CodeDataModel categoryData) {
        SearchOptionDataModel model = new SearchOptionDataModel();
        model.setCategory_name(categoryData.getName());
        model.setCategory_code(categoryData.getCode());
        int sizeType = -1;
        if (categoryData.getName().equals(SIZE_TYPE_BABY)) {
            sizeType = 2;
        } else if (categoryData.getName().equals(SIZE_TYPE_SHOES)) {
            sizeType = 4;
        } else if (categoryData.getName().equals(SIZE_TYPE_ACCESSORIES)) {
            sizeType = 5;
        } else {
            sizeType = 3;
        }
        model.setType(sizeType);
        view.navigateToProductBridge(model);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof MarketCategoryItemVH.RxBusEventMarketCategoryClick) {
                CodeDataModel categoryData = ((MarketCategoryItemVH.RxBusEventMarketCategoryClick) o).getCategoryData();
                navigateToProductBridge(categoryData);
            }
        }, Timber::e));
    }
}