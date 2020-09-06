package com.minilook.minilook.ui.event_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.network.event.EventRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;
import timber.log.Timber;

public class EventDetailPresenterImpl extends BasePresenterImpl implements EventDetailPresenter {

    private final View view;
    private final int event_id;
    private final EventRequest eventRequest;

    private Gson gson = new Gson();

    public EventDetailPresenterImpl(EventDetailArguments args) {
        view = args.getView();
        event_id = args.getEvent_id();
        eventRequest = new EventRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        view.setupEventImage("data");
        reqEventDetail();
    }

    private void reqEventDetail() {
        addDisposable(eventRequest.getEventDetail(event_id)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), String.class))
            .subscribe(this::resEventDetail, Timber::e));
    }

    private void resEventDetail(String data) {
        view.setupEventImage(data);
    }

    @Override public void onLoadMore() {
        //reqEvents();
    }

    //private void reqEvents() {
    //    addDisposable(promotionRequest.getPromotions(promotionId, ROWS, latestPromotionId)
    //        .compose(Transformer.applySchedulers())
    //        .filter(data -> data.getCode().equals(HttpCode.OK))
    //        .map((Function<BaseDataModel, List<PromotionDataModel>>)
    //            data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PromotionDataModel>>() {
    //            }.getType()))
    //        .subscribe(this::resTogetherPromotion, Timber::e));
    //}
    //
    //private void resTogetherPromotion(List<PromotionDataModel> data) {
    //    latestPromotionId = data.get(data.size() - 1).getPromotion_id();
    //    promotionAdapter.set(data);
    //    view.promotionRefresh();
    //}
}