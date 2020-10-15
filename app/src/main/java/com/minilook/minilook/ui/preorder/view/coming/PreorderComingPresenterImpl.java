package com.minilook.minilook.ui.preorder.view.coming;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.network.preorder.PreorderRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder.view.coming.di.PreorderComingArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PreorderComingPresenterImpl extends BasePresenterImpl implements PreorderComingPresenter {

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
    private final PreorderRequest preorderRequest;

    private Gson gson = new Gson();

    public PreorderComingPresenterImpl(PreorderComingArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        preorderRequest = new PreorderRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqPreorderComing();
    }

    private void reqPreorderComing() {
        addDisposable(preorderRequest.getComingPreorders()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {

                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<PreorderDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PreorderDataModel>>() {
                }.getType()))
            .subscribe(this::resPreorderComing, Timber::e));
    }

    private void resPreorderComing(List<PreorderDataModel> data) {
        adapter.set(data);
        view.refresh();
    }
}