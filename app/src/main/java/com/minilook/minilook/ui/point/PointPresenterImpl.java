package com.minilook.minilook.ui.point;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.user.PointDataModel;
import com.minilook.minilook.data.network.ipage.IpageRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.point.di.PointArguments;
import timber.log.Timber;

public class PointPresenterImpl extends BasePresenterImpl implements PointPresenter {

    private final View view;
    private final IpageRequest ipageRequest;

    private Gson gson = new Gson();

    public PointPresenterImpl(PointArguments args) {
        view = args.getView();
        ipageRequest = new IpageRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqPointDetail();
    }

    @Override public void onPointInfoClick() {
        view.navigateToWebView(URLKeys.URL_POINT);
    }

    private void reqPointDetail() {
        addDisposable(ipageRequest.getPointDetail()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.emptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), PointDataModel.class))
            .subscribe(this::resPointDetail, Timber::e));
    }

    private void resPointDetail(PointDataModel data) {

    }
}