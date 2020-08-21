package com.minilook.minilook.ui.review;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review.di.ReviewArguments;

public class ReviewPresenterImpl extends BasePresenterImpl implements ReviewPresenter {

    private final View view;

    public ReviewPresenterImpl(ReviewArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}