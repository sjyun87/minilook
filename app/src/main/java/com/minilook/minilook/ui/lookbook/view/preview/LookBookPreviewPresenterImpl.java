package com.minilook.minilook.ui.lookbook.view.preview;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.lookbook.LookBookRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
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
    private final Gson gson;

    private static final int DATA_POOL_SIZE = 30;
    private static final int ROWS = 10;
    private List<LookBookModuleDataModel> dataPool;

    private final List<Integer> usedLookbooks = new ArrayList<>();
    private int currentPosition = 0;

    public LookBookPreviewPresenterImpl(LookBookPreviewArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        lookBookRequest = new LookBookRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupViewPager();

        getLookBookModules();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onPageSelected(int position) {
        currentPosition = position;
        RxBus.send(new RxEventLookBookModuleChanged(adapter.get(position)));
    }

    @Override public void onLoadMore() {
        if (dataPool.size() > 0) setupLoadMoreData();
    }

    @Override public void onProductScrap(ProductDataModel $data) {
        replaceProductScrapData($data);
    }

    private void setupLoadMoreData() {
        int dataSize = Math.min(dataPool.size(), ROWS);
        int start = adapter.getSize();
        adapter.addAll(dataPool.subList(0, dataSize));
        view.refresh(start, dataSize);
        dataPool.subList(0, dataSize).clear();
        getMoreLookBookModules();
    }

    private void getLookBookModules() {
        addDisposable(
            lookBookRequest.getLookbookModules(ROWS, usedLookbooks)
                .compose(Transformer.applySchedulers())
                .filter(data -> {
                    String code = data.getCode();
                    if (!code.equals(HttpCode.OK)) {
                        view.showErrorDialog();
                    }
                    return code.equals(HttpCode.OK);
                })
                .map(data -> gson.fromJson(data.getData(), LookBookDataModel.class))
                .subscribe(this::onResLookBookModules, Timber::e)
        );
    }

    private void onResLookBookModules(LookBookDataModel data) {
        adapter.set(data.getLookbooks());
        view.refresh();
        addUsedData(data.getLookbooks());

        dataPool = new ArrayList<>();
        getMoreLookBookModules();
    }

    private void getMoreLookBookModules() {
        addDisposable(
            lookBookRequest.getLookbookModules(ROWS, usedLookbooks)
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), LookBookDataModel.class))
                .subscribe(this::onResMoreLookBookModules, Timber::e)
        );
    }

    private void onResMoreLookBookModules(LookBookDataModel data) {
        if (data.isReset()) usedLookbooks.clear();
        addUsedData(data.getLookbooks());

        if (data.getLookbooks().size() > 0) {
            dataPool.addAll(data.getLookbooks());
            if (dataPool.size() < DATA_POOL_SIZE) getMoreLookBookModules();
        }
    }

    private void addUsedData(List<LookBookModuleDataModel> lookbooks) {
        for (LookBookModuleDataModel model : lookbooks) {
            usedLookbooks.add(model.getLookbookNo());
        }
    }

    private void replaceProductScrapData(ProductDataModel $data) {
        for (int i = 0; i < adapter.getSize(); i++) {
            for (ProductDataModel product : adapter.get(i).getProducts()) {
                if (product.getProductNo() == $data.getProductNo()) {
                    product.setScrap($data.isScrap());
                    product.setScrapCount($data.getScrapCount());
                    product.setReviewCount($data.getReviewCount());

                    if (i == currentPosition) {
                        RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailUpdateScrap(adapter.get(i)));
                    }
                }
            }
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventLookBookScrollToNextModule) {
                view.scrollToNextModule();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookModuleChanged {
        private final LookBookModuleDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookScrollToNextModule {
    }
}