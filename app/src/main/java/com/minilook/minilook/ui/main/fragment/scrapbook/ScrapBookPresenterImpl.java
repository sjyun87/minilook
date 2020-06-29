package com.minilook.minilook.ui.main.fragment.scrapbook;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.scrapbook.di.ScrapBookArguments;

public class ScrapBookPresenterImpl extends BasePresenterImpl implements ScrapBookPresenter {

    private final View view;

    public ScrapBookPresenterImpl(ScrapBookArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        onProductClick();
    }

    @Override public void onProductClick() {
        view.setCurrentPage(0);
        view.setProductButtonUI();
    }

    @Override public void onBrandClick() {
        view.setCurrentPage(1);
        view.setBrandButtonUI();
    }

    @Override public void onRecentClick() {
        view.setCurrentPage(2);
        view.setRecentButtonUI();
    }
}