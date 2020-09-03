package com.minilook.minilook.ui.recent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.recent.RecentRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.recent.di.RecentArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class RecentPresenterImpl extends BasePresenterImpl implements RecentPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
    private final RecentRequest recentRequest;

    private Gson gson = new Gson();
    private int lastRecentId = -1;

    public RecentPresenterImpl(RecentArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        recentRequest = new RecentRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqRecentProducts();
    }

    @Override public void onLoadMore() {
        reqLoadMoreRecentProducts();
    }

    @Override public void onEmptyClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToPage(BottomBar.POSITION_MARKET));
        view.finish();
    }

    private void reqRecentProducts() {
        addDisposable(recentRequest.getRecentProducts(lastRecentId, ROWS)
            .map((Function<BaseDataModel, List<ProductDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
                }.getType()))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resRecentProducts, Timber::e));
    }

    private void resRecentProducts(List<ProductDataModel> data) {
        if (data.size() > 0) {
            lastRecentId = data.get(data.size() - 1).getRecent_id();

            adapter.set(data);
            view.refresh();
        } else {
            view.showEmptyPanel();
        }
    }

    private void reqLoadMoreRecentProducts() {
        addDisposable(recentRequest.getRecentProducts(lastRecentId, ROWS)
            .map((Function<BaseDataModel, List<ProductDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
                }.getType()))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resLoadMoreRecentProducts, Timber::e));
    }

    private void resLoadMoreRecentProducts(List<ProductDataModel> data) {
        if (data.size() > 0) {
            lastRecentId = data.get(data.size() - 1).getRecent_id();
            int start = adapter.getSize();
            int rows = data.size();

            adapter.addAll(data);
            view.refresh(start, rows);
        }
    }
}