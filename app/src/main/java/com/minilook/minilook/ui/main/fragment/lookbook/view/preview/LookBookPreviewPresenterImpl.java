package com.minilook.minilook.ui.main.fragment.lookbook.view.preview;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookDetailDataModel;
import com.minilook.minilook.data.network.lookbook.LookBookRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.di.LookBookPreviewArguments;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPreviewPresenterImpl extends BasePresenterImpl implements LookBookPreviewPresenter {

    private final View view;
    private final BaseAdapterDataModel<LookBookDataModel> adapter;
    private final LookBookRequest lookBookRequest;

    private AtomicInteger page;

    private static final int DATA_POOL_SIZE = 30;
    private List<LookBookDataModel> dataPool;
    private boolean isDataEnd = false;

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
        if (dataPool.size() > 0 && position == adapter.getSize() - 3) setupLoadMoreData();
        RxBus.send(new RxEventLookBookModuleChanged(adapter.get(position).getDetail()));
    }

    private void setupLoadMoreData() {
        adapter.addAll(dataPool.subList(0, 10));
        view.refresh();
        dataPool.subList(0, 10).clear();
        if (!isDataEnd) reqLoadMoreLookBookModules();
    }

    private void reqLookBookModules() {
        page = new AtomicInteger(0);
        addDisposable(
            lookBookRequest.getLookbookModules(page.getAndIncrement())
                .compose(Transformer.applySchedulers())
                .subscribe(this::resLookBookModules, Timber::e)
        );
    }

    private void resLookBookModules(List<LookBookDataModel> data) {
        adapter.set(data);
        view.refresh();

        dataPool = new ArrayList<>();
        reqLoadMoreLookBookModules();
    }

    private void reqLoadMoreLookBookModules() {
        addDisposable(
            lookBookRequest.getLookbookModules(page.getAndIncrement())
                .observeOn(SchedulersFacade.io())
                .subscribeOn(SchedulersFacade.io())
                .subscribe(this::resLoadMoreLookBookModules, Timber::e)
        );
    }

    private void resLoadMoreLookBookModules(List<LookBookDataModel> data) {
        if (data.size() < 10) isDataEnd = true;
        if (data.size() > 0) {
            dataPool.addAll(data);
            if (dataPool.size() < DATA_POOL_SIZE) reqLoadMoreLookBookModules();
        }
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookModuleChanged {
        private LookBookDetailDataModel data;
    }
}