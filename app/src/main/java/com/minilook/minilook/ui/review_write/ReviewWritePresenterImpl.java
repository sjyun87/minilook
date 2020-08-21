package com.minilook.minilook.ui.review_write;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;

public class ReviewWritePresenterImpl extends BasePresenterImpl implements ReviewWritePresenter {

    private final View view;

    public ReviewWritePresenterImpl(ReviewWriteArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}