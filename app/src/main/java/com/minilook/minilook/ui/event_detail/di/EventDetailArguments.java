package com.minilook.minilook.ui.event_detail.di;

import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.event_detail.EventDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class EventDetailArguments {
    private final EventDetailPresenter.View view;
    private final int event_id;
    private final BaseAdapterDataModel<String> adapter;
}