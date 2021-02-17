package com.minilook.minilook.ui.review_history.view.writable;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.data.model.review.WritableReviewHistoryDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_history.view.writable.di.WritableReviewArguments;
import com.minilook.minilook.ui.review_write.ReviewWritePresenterImpl;
import java.util.List;
import timber.log.Timber;

public class WritableReviewPresenterImpl extends BasePresenterImpl implements WritableReviewPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<OrderHistoryDataModel> adapter;
    private final ReviewRequest reviewRequest;
    private final Gson gson;

    private long lastOrderTime;

    public WritableReviewPresenterImpl(WritableReviewArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        reviewRequest = new ReviewRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupRecyclerView();

        getWritableReviews();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLoadMore() {
        getMoreWritableReviews();
    }

    private void getWritableReviews() {
        addDisposable(reviewRequest.getWritableReviews(ROWS, lastOrderTime)
            .compose(Transformer.applySchedulers())
            .filter(model -> {
                String code = model.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                } else if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), WritableReviewHistoryDataModel.class))
            .subscribe(this::onResWrittenReviews, Timber::e));
    }

    private void onResWrittenReviews(WritableReviewHistoryDataModel data) {
        List<OrderHistoryDataModel> orders = data.getOrders();
        lastOrderTime = orders.get(orders.size() - 1).getRegistDate();
        adapter.set(orders);
        view.refresh();
    }

    private void getMoreWritableReviews() {
        addDisposable(reviewRequest.getWritableReviews(ROWS, lastOrderTime)
            .compose(Transformer.applySchedulers())
            .filter(model -> {
                String code = model.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), WritableReviewHistoryDataModel.class))
            .subscribe(this::onResMoreWrittenReviews, Timber::e));
    }

    private void onResMoreWrittenReviews(WritableReviewHistoryDataModel data) {
        List<OrderHistoryDataModel> orders = data.getOrders();
        lastOrderTime = orders.get(orders.size() - 1).getRegistDate();
        int start = adapter.getSize();
        int row = orders.size();
        adapter.addAll(orders);
        view.refresh(start, row);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof ReviewWritePresenterImpl.RxEventReviewWrite) {
                lastOrderTime = -1;
                getWritableReviews();
            }
        }, Timber::e));
    }
}