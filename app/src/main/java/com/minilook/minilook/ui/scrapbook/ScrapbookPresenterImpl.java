package com.minilook.minilook.ui.scrapbook;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.scrapbook.di.ScrapbookArguments;
import timber.log.Timber;

public class ScrapbookPresenterImpl extends BasePresenterImpl implements ScrapbookPresenter {

    private final View view;

    public ScrapbookPresenterImpl(ScrapbookArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onTabClick(int position) {
        view.setupCurrentPage(position);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof MainPresenterImpl.RxEventNavigateToPage) {
                view.finish();
            }
        }, Timber::e));
    }
}