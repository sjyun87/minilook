package com.minilook.minilook.ui.main;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;

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

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToDetail) {
                String url = ((RxEventNavigateToDetail) o).getUrl();
                view.navigateToDetail(url);
            } else if (o instanceof RxEventNavigateToBrand) {
                int brandId = ((RxEventNavigateToBrand) o).getBrandId();
                view.navigateToBrand(brandId);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToBrand {
        private int brandId;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToDetail {
        private String url;
    }
}