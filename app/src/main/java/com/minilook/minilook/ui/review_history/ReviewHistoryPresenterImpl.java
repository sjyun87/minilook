package com.minilook.minilook.ui.review_history;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_history.di.ReviewHistoryArguments;

public class ReviewHistoryPresenterImpl extends BasePresenterImpl implements ReviewHistoryPresenter {

    private final View view;

    public ReviewHistoryPresenterImpl(ReviewHistoryArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

        view.setupClickAction();
        view.setupViewPager();
    }

    @Override public void onWrittenReviewClick() {
        setWrittenReviewButton();
        view.setCurrentPage(0);
    }

    @Override public void onWritableReviewClick() {
        setWritableReviewButton();
        view.setCurrentPage(1);
    }

    @Override public void onPageSelected(int position) {
        if (position == 0) {
            setWrittenReviewButton();
        } else {
            setWritableReviewButton();
        }
    }

    private void setWrittenReviewButton() {
        view.enableWrittenReviewButton();
        view.disableWritableReviewButton();
    }

    private void setWritableReviewButton() {
        view.disableWrittenReviewButton();
        view.enableWritableReviewButton();
    }
}