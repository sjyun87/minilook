package com.minilook.minilook.ui.review_history.view.writable;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_history.view.writable.di.ReviewWritableArguments;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewWritablePresenterImpl extends BasePresenterImpl implements ReviewWritablePresenter {

    private static final int ROWS = 30;

    private final View view;
    private final Gson gson;

    private AtomicInteger page = new AtomicInteger(0);
    private int totalPageSize;

    public ReviewWritablePresenterImpl(ReviewWritableArguments args) {
        view = args.getView();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        view.setupRecyclerView();

    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLoadMore() {
        
    }
}