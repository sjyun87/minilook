package com.minilook.minilook.ui.scrapbook;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrapbook.di.ScrapbookArguments;

public class ScrapbookPresenterImpl extends BasePresenterImpl implements ScrapbookPresenter {

    private final View view;

    public ScrapbookPresenterImpl(ScrapbookArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
    }
}