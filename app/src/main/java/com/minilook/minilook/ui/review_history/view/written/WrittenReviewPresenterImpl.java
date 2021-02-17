package com.minilook.minilook.ui.review_history.view.written;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.model.review.ReviewHistoryDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_history.view.written.di.ReviewWrittenArguments;
import com.minilook.minilook.ui.review_write.ReviewWritePresenterImpl;
import java.util.List;
import timber.log.Timber;

public class WrittenReviewPresenterImpl extends BasePresenterImpl implements WrittenReviewPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ReviewDataModel> adapter;
    private final ReviewRequest reviewRequest;
    private final Gson gson;

    private int lastReviewNo;

    public WrittenReviewPresenterImpl(ReviewWrittenArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        reviewRequest = new ReviewRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupRecyclerView();

        getWrittenReviews();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLoadMore() {
        getMoreWrittenReviews();
    }

    private void getWrittenReviews() {
        addDisposable(reviewRequest.getWrittenReviews(ROWS, lastReviewNo)
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
            .map(model -> gson.fromJson(model.getData(), ReviewHistoryDataModel.class))
            .subscribe(this::onResWrittenReviews, Timber::e));
    }

    private void onResWrittenReviews(ReviewHistoryDataModel data) {
        List<ReviewDataModel> reviews = data.getReviews();
        lastReviewNo = reviews.get(reviews.size() - 1).getReviewNo();
        adapter.set(reviews);
        view.refresh();
    }

    private void getMoreWrittenReviews() {
        addDisposable(reviewRequest.getWrittenReviews(ROWS, lastReviewNo)
            .compose(Transformer.applySchedulers())
            .filter(model -> {
                String code = model.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), ReviewHistoryDataModel.class))
            .subscribe(this::onResMoreWrittenReviews, Timber::e));
    }

    private void onResMoreWrittenReviews(ReviewHistoryDataModel data) {
        List<ReviewDataModel> reviews = data.getReviews();
        lastReviewNo = reviews.get(reviews.size() - 1).getReviewNo();
        int start = adapter.getSize();
        int row = reviews.size();
        adapter.addAll(reviews);
        view.refresh(start, row);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof ReviewWritePresenterImpl.RxEventReviewWrite) {
                lastReviewNo = -1;
                getWrittenReviews();
            }
        }, Timber::e));
    }
}