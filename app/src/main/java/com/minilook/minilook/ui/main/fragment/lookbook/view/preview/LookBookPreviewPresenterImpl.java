package com.minilook.minilook.ui.main.fragment.lookbook.view.preview;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.network.lookbook.LookBookRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.di.LookBookPreviewArguments;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPreviewPresenterImpl extends BasePresenterImpl implements LookBookPreviewPresenter {

    private final View view;
    private final BaseAdapterDataModel<LookBookModuleDataModel> adapter;
    private final LookBookRequest lookBookRequest;

    private AtomicInteger page;

    public LookBookPreviewPresenterImpl(LookBookPreviewArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        lookBookRequest = new LookBookRequest();
    }

    @Override public void onCreate() {
        view.setupViewPager();
        reqLookBookModules();
    }

    @Override public void onPageSelected(int position) {
        RxBus.send(new RxEventLookBookPageChange(adapter.get(position)));
    }

    private void reqLookBookModules() {
        page = new AtomicInteger(0);
        addDisposable(
            lookBookRequest.getLookbookModules(page.get())
                .compose(Transformer.applySchedulers())
                .subscribe(this::resLookBookModules, Timber::e)
        );
    }

    private void resLookBookModules(LookBookDataModel data) {
        adapter.set(data.getModules());
        view.refresh();
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookPageChange {
        private LookBookModuleDataModel data;
    }
}