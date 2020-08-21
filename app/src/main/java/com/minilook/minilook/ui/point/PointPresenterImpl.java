package com.minilook.minilook.ui.point;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.point.di.PointArguments;

public class PointPresenterImpl extends BasePresenterImpl implements PointPresenter {

    private final View view;

    public PointPresenterImpl(PointArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}