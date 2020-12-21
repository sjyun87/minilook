package com.minilook.minilook.ui.lookbook;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.di.LookBookArguments;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.util.TrackingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPresenterImpl extends BasePresenterImpl implements LookBookPresenter {

    private final View view;

    public LookBookPresenterImpl(LookBookArguments args) {
        view = args.getView();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupViewPager();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("룩북페이지", LookBookFragment.class.getSimpleName());
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onPageSelected(int position) {
        RxBus.send(new MainPresenterImpl.RxEventChangeBottomBarTheme(position != 0));
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventScrollToPreview) {
                boolean smoothScroll = ((RxEventScrollToPreview) o).isSmoothScroll();
                view.scrollToPreviewPage(smoothScroll);
            } else if (o instanceof RxEventScrollToDetail) {
                boolean smoothScroll = ((RxEventScrollToDetail) o).isSmoothScroll();
                view.scrollToDetailPage(smoothScroll);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventScrollToPreview {
        private final boolean smoothScroll;
    }

    @AllArgsConstructor @Getter public final static class RxEventScrollToDetail {
        private final boolean smoothScroll;
    }
}