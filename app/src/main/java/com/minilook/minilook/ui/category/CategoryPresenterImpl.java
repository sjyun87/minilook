package com.minilook.minilook.ui.category;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.category.di.CategoryArguments;

public class CategoryPresenterImpl extends BasePresenterImpl implements CategoryPresenter {

    private final View view;

    public CategoryPresenterImpl(CategoryArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}