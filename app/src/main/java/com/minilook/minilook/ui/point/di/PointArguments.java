package com.minilook.minilook.ui.point.di;

import com.minilook.minilook.data.model.user.PointHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.point.PointPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PointArguments {
    private final PointPresenter.View view;
    private final BaseAdapterDataModel<PointHistoryDataModel> adapter;
}