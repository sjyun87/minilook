package com.minilook.minilook.ui.event_detail.di;

import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.event_detail.EventDetailPresenter;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class EventDetailArguments {
    private final EventDetailPresenter.View view;
    private final int eventNo;
    private final BaseAdapterDataModel<EventDataModel> adapter;
    private final DynamicLinkManager dynamicLinkManager;
}