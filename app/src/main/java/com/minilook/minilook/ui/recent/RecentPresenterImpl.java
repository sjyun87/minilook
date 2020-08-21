package com.minilook.minilook.ui.recent;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.recent.di.RecentArguments;

public class RecentPresenterImpl extends BasePresenterImpl implements RecentPresenter {

    private final View view;

    public RecentPresenterImpl(RecentArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}