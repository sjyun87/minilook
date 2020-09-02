package com.minilook.minilook.ui.lookbook;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.di.LookBookArguments;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPresenterImpl extends BasePresenterImpl implements LookBookPresenter {

    private final View view;

    public LookBookPresenterImpl(LookBookArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        toRxObservable();

        view.setupViewPager();
    }

    @Override public void onPageSelected(int position) {
        RxBus.send(new MainPresenterImpl.RxEventLookBookPageChanged(position));
        if (position == 0) RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailScrollToTop());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToPreview) {
                boolean smoothScroll = ((RxEventNavigateToPreview) o).isSmoothScroll();
                view.navigateToPreviewPage(smoothScroll);
            } else if (o instanceof RxEventNavigateToDetail) {
                boolean smoothScroll = ((RxEventNavigateToDetail) o).isSmoothScroll();
                view.navigateToDetailPage(smoothScroll);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToPreview {
        boolean smoothScroll;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToDetail {
        boolean smoothScroll;
    }
}