package com.minilook.minilook.ui.main.fragment.lookbook;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.di.LookBookArguments;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.viewholder.LookBookImageModuleVH;
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
        RxBus.send(new RxEventLookBookPageChanged(position));
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookImageModuleVH.RxEventPreviewClick) {
                view.navigateToDetailPage();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookPageChanged {
        private int position;
    }
}