package com.minilook.minilook.ui.event_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;

public class EventDetailPresenterImpl extends BasePresenterImpl implements EventDetailPresenter {

    private final View view;
    private final int event_id;

    public EventDetailPresenterImpl(EventDetailArguments args) {
        view = args.getView();
        event_id = args.getEvent_id();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}