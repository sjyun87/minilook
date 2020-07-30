package com.minilook.minilook.ui.preorder;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PreorderPresenterImpl extends BasePresenterImpl implements PreorderPresenter {

    private final View view;

    public PreorderPresenterImpl(PreorderArguments args) {
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