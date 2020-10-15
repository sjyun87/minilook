package com.minilook.minilook.ui.preorder;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class PreorderPresenterImpl extends BasePresenterImpl implements PreorderPresenter {

    private final View view;

    public PreorderPresenterImpl(PreorderArguments args) {
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
            if (o instanceof RxBusEventPreorderInfoClick) {
                view.navigateToPreorderInfo();
            } else if (o instanceof RxBusEventClosePreorderEmpty) {
                view.hideClosePreorderTab();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventPreorderInfoClick {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventClosePreorderEmpty {
    }
}