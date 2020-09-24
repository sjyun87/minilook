package com.minilook.minilook.ui.lookbook.view.preview;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.network.lookbook.LookBookRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.view.preview.di.LookBookPreviewArguments;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPreviewPresenterImpl extends BasePresenterImpl implements LookBookPreviewPresenter {

    private final View view;
    private final BaseAdapterDataModel<LookBookModuleDataModel> adapter;
    private final LookBookRequest lookBookRequest;

    private Gson gson = new Gson();

    private static final int DATA_POOL_SIZE = 30;
    private static final int ROWS = 10;
    private List<LookBookModuleDataModel> dataPool;

    private List<Integer> usedLookbooks = new ArrayList<>();

    public LookBookPreviewPresenterImpl(LookBookPreviewArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        lookBookRequest = new LookBookRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();

        reqLookBookModules();
    }

    @Override public void onPageSelected(int position) {
        if (dataPool.size() > 0 && position == adapter.getSize() - 3) setupLoadMoreData();
        RxBus.send(new RxEventLookBookModuleChanged(adapter.get(position)));
    }

    private void setupLoadMoreData() {
        int dataSize = Math.min(dataPool.size(), ROWS);
        int start = adapter.getSize();
        adapter.addAll(dataPool.subList(0, dataSize));
        view.refresh(start, dataSize);
        dataPool.subList(0, dataSize).clear();
        reqLoadMoreLookBookModules();
    }

    private void reqLookBookModules() {
        addDisposable(
            lookBookRequest.getLookbookModules(ROWS, usedLookbooks)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), LookBookDataModel.class))
                .subscribe(this::resLookBookModules, Timber::e)
        );
    }

    private void resLookBookModules(LookBookDataModel data) {
        adapter.set(data.getLookbooks());
        view.refresh();
        usedData(data.getLookbooks());

        dataPool = new ArrayList<>();
        reqLoadMoreLookBookModules();
    }

    private void reqLoadMoreLookBookModules() {
        addDisposable(
            lookBookRequest.getLookbookModules(ROWS, usedLookbooks)
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), LookBookDataModel.class))
                .subscribe(this::resLoadMoreLookBookModules, Timber::e)
        );
    }

    private void resLoadMoreLookBookModules(LookBookDataModel data) {
        if (data.isReset()) usedLookbooks.clear();
        usedData(data.getLookbooks());

        if (data.getLookbooks().size() > 0) {
            dataPool.addAll(data.getLookbooks());
            if (dataPool.size() < DATA_POOL_SIZE) reqLoadMoreLookBookModules();
        }
    }

    private void usedData(List<LookBookModuleDataModel> lookbooks) {
        for (LookBookModuleDataModel model : lookbooks) {
            usedLookbooks.add(model.getLookbook_no());
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventLookBookCoachMark1) {
                view.scrollToNextPage();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookModuleChanged {
        private LookBookModuleDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookCoachMark1 {
    }
}