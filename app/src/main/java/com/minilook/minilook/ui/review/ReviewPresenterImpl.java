package com.minilook.minilook.ui.review;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.model.review.ReviewHistoryDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review.di.ReviewArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ReviewPresenterImpl extends BasePresenterImpl implements ReviewPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final int productNo;
    private final BaseAdapterDataModel<ReviewDataModel> adapter;
    private final ReviewRequest reviewRequest;

    private Gson gson = new Gson();

    private int lastReviewNo = 0;

    public ReviewPresenterImpl(ReviewArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        adapter = args.getAdapter();
        reviewRequest = new ReviewRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqReviews();
    }

    @Override public void onLoadMore() {
        reqLoadMoreReviews();
    }

    private void reqReviews() {
        addDisposable(reviewRequest.getReviews(productNo, ROWS, lastReviewNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.emptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ReviewHistoryDataModel.class))
            .subscribe(this::resReviews, Timber::e));
    }

    private void resReviews(ReviewHistoryDataModel data) {
        view.setTotalCount(data.getReviewCount());

        adapter.set(data.getReviews());
        view.refresh();
        lastReviewNo = adapter.get(adapter.getSize() - 1).getReviewNo();
    }

    private void reqLoadMoreReviews() {
        addDisposable(reviewRequest.getReviews(productNo, ROWS, lastReviewNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ReviewHistoryDataModel.class))
            .subscribe(this::resLoadMoreReviews, Timber::e));
    }

    private void resLoadMoreReviews(ReviewHistoryDataModel data) {
        int start = adapter.getSize();
        adapter.addAll(data.getReviews());
        view.refresh(start, data.getReviews().size());
        lastReviewNo = adapter.get(adapter.getSize() - 1).getReviewNo();
    }

    private void reqUpdateHelp(boolean isHelp, int reviewNo) {
        addDisposable(reviewRequest.updateHelp(isHelp, productNo, reviewNo)
            .subscribe());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventReviewHelpClick) {
                boolean isHelp = ((RxEventReviewHelpClick) o).isHelp();
                int reviewNo = ((RxEventReviewHelpClick) o).getReviewNo();
                reqUpdateHelp(isHelp, reviewNo);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventReviewHelpClick {
        boolean isHelp;
        int reviewNo;
    }
}