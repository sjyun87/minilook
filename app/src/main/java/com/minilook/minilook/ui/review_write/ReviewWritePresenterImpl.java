package com.minilook.minilook.ui.review_write;

import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ReviewWritePresenterImpl extends BasePresenterImpl implements ReviewWritePresenter {

    private final View view;
    private final String receiptNo;
    private final OrderProductDataModel data;
    private final ReviewRequest reviewRequest;

    private String review;

    public ReviewWritePresenterImpl(ReviewWriteArguments args) {
        view = args.getView();
        receiptNo = args.getReceiptNo();
        data = args.getData();
        reviewRequest = new ReviewRequest();
    }

    @Override public void onCreate() {
        view.setThumb(data.getThumbUrl());
        view.setBrandName(data.getBrandName());
        view.setProductName(data.getName());
        view.setOption(data.getColorName(), data.getSizeName());
        view.setupReviewEditText();
    }

    @Override public void onTextChanged(String text) {
        review = text;
        if (review.length() >= 5) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    @Override public void onApplyClick() {
        reqWriteReview();
    }

    private void reqWriteReview() {
        addDisposable(reviewRequest.writeReview(receiptNo, data.getProductNo(), data.getOptionNo(), review)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resWriteReview, Timber::e));
    }

    private void resWriteReview(BaseDataModel data) {
        view.showReviewWriteToast();
        RxBus.send(new RxEventReviewWrite());
        view.finish();
    }

    @AllArgsConstructor @Getter public final static class RxEventReviewWrite {
    }
}