package com.minilook.minilook.ui.preorder.view.open;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.network.preorder.PreorderRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.view.open.di.PreorderOpenArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PreorderOpenPresenterImpl extends BasePresenterImpl implements PreorderOpenPresenter {

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
    private final PreorderRequest preorderRequest;

    private Gson gson = new Gson();

    public PreorderOpenPresenterImpl(PreorderOpenArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        preorderRequest = new PreorderRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        reqPreorderOpen();
    }

    private void reqPreorderOpen() {
        addDisposable(preorderRequest.getOpenPreorders()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<PreorderDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PreorderDataModel>>() {
                }.getType()))
            .subscribe(this::resPreorderOpen, Timber::e));
    }

    private void resPreorderOpen(List<PreorderDataModel> data) {
        adapter.set(data);
        view.refresh();
    }
}