package com.minilook.minilook.ui.main.fragment.lookbook.view.preview;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.model.base.ResponseDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.network.lookbook.LookBookRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.di.LookBookPreviewArguments;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.functions.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookPreviewPresenterImpl extends BasePresenterImpl implements LookBookPreviewPresenter {

    private final View view;
    private final BaseAdapterDataModel<LookBookModuleDataModel> adapter;
    private final LookBookRequest lookBookRequest;

    private AtomicInteger page;
    private Gson gson = new Gson();

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
                .map((Function<ResponseDataModel, List<LookBookDataModel>>) result -> {
                    Type data = new TypeToken<List<LookBookDataModel>>() {
                    }.getType();
                    return gson.fromJson(result.getDatas(), data);
                })
                .compose(Transformer.applySchedulers())
                .subscribe(this::resLookBookModules, Timber::e)
        );
    }

    private void resLookBookModules(List<LookBookDataModel> data) {
        adapter.set(data);
        view.refresh();
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookPageChange {
        private LookBookModuleDataModel data;
    }
}