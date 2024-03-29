package com.minilook.minilook.ui.point;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.member.PointDataModel;
import com.minilook.minilook.data.model.member.PointHistoryDataModel;
import com.minilook.minilook.data.network.ipage.IpageRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.point.di.PointArguments;
import timber.log.Timber;

public class PointPresenterImpl extends BasePresenterImpl implements PointPresenter {

    private final View view;
    private final BaseAdapterDataModel<PointDataModel> adapter;
    private final IpageRequest ipageRequest;

    private Gson gson = new Gson();

    public PointPresenterImpl(PointArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
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
        addDisposable(ipageRequest.getPointHistory()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), PointHistoryDataModel.class))
            .subscribe(this::resPointDetail, Timber::e));
    }

    private void resPointDetail(PointHistoryDataModel data) {
        view.setupPoint(data.getPoint());
        if (data.getHistory().size() > 0) {
            adapter.set(data.getHistory());
            view.refresh();
        } else {
            view.emptyPanel();
        }
    }
}