package com.minilook.minilook.ui.main;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.ui.main.fragment.lookbook.LookBookPresenterImpl;
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
            RxBus.send(new RxEventTabChanged());
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToProductDetail) {
                String url = ((RxEventNavigateToProductDetail) o).getUrl();
                view.navigateToProductDetail(url);
            } else if (o instanceof RxEventNavigateToBrandDetail) {
                int brandId = ((RxEventNavigateToBrandDetail) o).getBrandId();
                view.navigateToBrandDetail(brandId);
            } else if (o instanceof LookBookPresenterImpl.RxEventLookBookPageChanged) {
                int position = ((LookBookPresenterImpl.RxEventLookBookPageChanged) o).getPosition();
                view.setupBottombarTheme(position != 0);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToBrandDetail {
        private int brandId;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToProductDetail {
        private String url;
    }

    @AllArgsConstructor @Getter public final static class RxEventTabChanged {
    }
}