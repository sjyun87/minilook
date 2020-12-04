package com.minilook.minilook.ui.event_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    private Gson gson = new Gson();
    private EventDataModel data;
    private int latestEventId = -1;

    public EventDetailPresenterImpl(EventDetailArguments args) {
        view = args.getView();
        eventNo = args.getEventNo();
        eventAdapter = args.getAdapter();
        eventRequest = new EventRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqEventDetail();
        reqEvents();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("이벤트 상세페이지", EventDetailActivity.class.getSimpleName());
    }

    private void reqEventDetail() {
        addDisposable(eventRequest.getEventDetail(eventNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), EventDataModel.class))
            .subscribe(this::resEventDetail, Timber::e));
    }

    private void resEventDetail(EventDataModel data) {
        this.data = data;

        view.setupEventImage(data.getEventUrl());
    }

    @Override public void onLoadMore() {
        reqEvents();
    }

    @Override public void onShareClick() {
        DynamicLinkUtil.sendDynamicLink(DynamicLinkUtil.TYPE_EVENT, eventNo, data.getTitle(), data.getThumbUrl());
    }

    private void reqEvents() {
        addDisposable(eventRequest.getEvents(eventNo, latestEventId, ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<EventDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<EventDataModel>>() {
                }.getType()))
            .subscribe(this::resTogetherPromotion, Timber::e));
    }

    private void resTogetherPromotion(List<EventDataModel> data) {
        latestEventId = data.get(data.size() - 1).getEventNo();
        int start = eventAdapter.getSize();
        eventAdapter.addAll(data);
        view.refresh(start, data.size());
    }
}