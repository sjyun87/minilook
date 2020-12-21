package com.minilook.minilook.ui.event_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.data.network.event.EventRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.minilook.minilook.util.TrackingUtil;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class EventDetailPresenterImpl extends BasePresenterImpl implements EventDetailPresenter {

    private static final int ROWS = 10;

    private final View view;
    private final int eventNo;
    private final BaseAdapterDataModel<EventDataModel> eventAdapter;
    private final EventRequest eventRequest;
    private final DynamicLinkUtil dynamicLinkUtil;
    private final Gson gson;

    private EventDataModel data;
    private int latestEventNo = -1;

    public EventDetailPresenterImpl(EventDetailArguments args) {
        view = args.getView();
        eventNo = args.getEventNo();
        eventAdapter = args.getAdapter();
        eventRequest = new EventRequest();
        dynamicLinkUtil = new DynamicLinkUtil();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupRecyclerView();

        getEventDetail();
        getOtherEvents();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("이벤트 상세페이지", EventDetailActivity.class.getSimpleName());
    }

    @Override public void onDestroy() {
        view.clear();
    }

    private void getEventDetail() {
        addDisposable(eventRequest.getEventDetail(eventNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), EventDataModel.class))
            .subscribe(this::onResEventDetail, Timber::e));
    }

    private void onResEventDetail(EventDataModel data) {
        this.data = data;

        view.setEventImage(data.getEventUrl());
    }

    @Override public void onLoadMore() {
        if (eventAdapter.getSize() >= ROWS) getMoreOtherEvents();
    }

    @Override public void onShareClick() {
        dynamicLinkUtil.createLink(DynamicLinkUtil.TYPE_EVENT, eventNo, data.getTitle(), data.getThumbUrl(),
            new DynamicLinkUtil.OnDynamicLinkListener() {
                @Override public void onSuccess(String link) {
                    view.sendDynamicLink(link);
                }

                @Override public void onError() {
                    view.showErrorDialog();
                }
            });
    }

    private void getOtherEvents() {
        addDisposable(eventRequest.getEvents(eventNo, ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.hideOtherEvents();
                } else if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return data.getCode().equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<EventDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<EventDataModel>>() {
                }.getType()))
            .subscribe(this::onResOtherEvents, Timber::e));
    }

    private void onResOtherEvents(List<EventDataModel> data) {
        latestEventNo = data.get(data.size() - 1).getEventNo();
        eventAdapter.set(data);
        view.refresh();
    }

    private void getMoreOtherEvents() {
        addDisposable(eventRequest.getEvents(eventNo, ROWS, latestEventNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<EventDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<EventDataModel>>() {
                }.getType()))
            .subscribe(this::onResMoreOtherEvents, Timber::e));
    }

    private void onResMoreOtherEvents(List<EventDataModel> data) {
        latestEventNo = data.get(data.size() - 1).getEventNo();
        int start = eventAdapter.getSize();
        eventAdapter.addAll(data);
        view.refresh(start, data.size());
    }
}