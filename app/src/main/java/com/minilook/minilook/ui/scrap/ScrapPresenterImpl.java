package com.minilook.minilook.ui.scrap;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrap.di.ScrapArguments;

public class ScrapPresenterImpl extends BasePresenterImpl implements ScrapPresenter {

    private final View view;

    public ScrapPresenterImpl(ScrapArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
        view.scrollToTop();
    }
}