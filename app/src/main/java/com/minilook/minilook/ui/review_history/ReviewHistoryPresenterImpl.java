package com.minilook.minilook.ui.review_history;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_history.di.ReviewHistoryArguments;

public class ReviewHistoryPresenterImpl extends BasePresenterImpl implements ReviewHistoryPresenter {

    private final View view;

    public ReviewHistoryPresenterImpl(ReviewHistoryArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

    }
}