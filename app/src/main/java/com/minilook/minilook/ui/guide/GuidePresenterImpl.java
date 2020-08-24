package com.minilook.minilook.ui.guide;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.guide.di.GuideArguments;

public class GuidePresenterImpl extends BasePresenterImpl implements GuidePresenter {

    private final View view;

    public GuidePresenterImpl(GuideArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupViewPager();
    }
}