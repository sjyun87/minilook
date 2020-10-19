package com.minilook.minilook.ui.preorder.view.close;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.model.preorder.PreorderPageDataModel;
import com.minilook.minilook.data.network.preorder.PreorderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.view.close.di.PreorderCloseArguments;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class PreorderClosePresenterImpl extends BasePresenterImpl implements PreorderClosePresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
    private final PreorderRequest preorderRequest;

    private Gson gson = new Gson();
    private AtomicInteger page;
    private int totalPage = 0;
    private int lastPreorderNo;

    public PreorderClosePresenterImpl(PreorderCloseArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        preorderRequest = new PreorderRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        reqPreorderClose();
    }

    @Override public void onLoadMore() {
        if (totalPage > page.get()) reqLoadMorePreorderClose();
    }

    private void reqPreorderClose() {
        page = new AtomicInteger(1);
        addDisposable(preorderRequest.getClosePreorders(ROWS, lastPreorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    RxBus.send(new RxBusEventClosePreorderEmpty());
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PreorderPageDataModel.class))
            .subscribe(this::resPreorderClose, Timber::e));
    }

    private void resPreorderClose(PreorderPageDataModel data) {
        totalPage = data.getTotal();
        List<PreorderDataModel> items = data.getPreorders();
        lastPreorderNo = items.get(items.size() - 1).getPreorderNo();
        adapter.set(items);
        view.refresh();
    }

    private void reqLoadMorePreorderClose() {
        page.incrementAndGet();
        addDisposable(preorderRequest.getClosePreorders(ROWS, lastPreorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PreorderPageDataModel.class))
            .subscribe(this::resLoadMorePreorderClose, Timber::e));
    }

    private void resLoadMorePreorderClose(PreorderPageDataModel data) {
        List<PreorderDataModel> items = data.getPreorders();
        lastPreorderNo = items.get(items.size() - 1).getPreorderNo();
        int start = adapter.getSize();
        adapter.addAll(items);
        view.refresh(start, items.size());
    }

    @AllArgsConstructor @Getter public final static class RxBusEventClosePreorderEmpty {
    }
}