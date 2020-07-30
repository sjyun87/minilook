package com.minilook.minilook.ui.main;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private final View view;

    public MainPresenterImpl(MainArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();
        view.setupBottomBar();
    }

    @Override public void onTabChanged(int position) {
        if (position != 0) {
            RxBus.send(new LookBookPresenterImpl.RxEventNavigateToPreview(false));
            RxBus.send(new LookBookDetailPresenterImpl.RxEventScrollToTop());
        }
        view.setupCurrentPage(position);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToBrandDetail) {
                int id = ((RxEventNavigateToBrandDetail) o).getBrandId();
                view.navigateToBrandDetail(id);
            } else if (o instanceof LookBookPresenterImpl.RxEventLookBookPageChanged) {
                int position = ((LookBookPresenterImpl.RxEventLookBookPageChanged) o).getPosition();
                view.setupBottomBarTheme(position != 0);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToBrandDetail {
        private int brandId;
    }
}