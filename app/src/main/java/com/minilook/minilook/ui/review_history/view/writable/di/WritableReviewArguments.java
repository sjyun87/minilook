package com.minilook.minilook.ui.review_history.view.writable.di;

import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.review_history.view.writable.WritableReviewPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class WritableReviewArguments {
    private final WritableReviewPresenter.View view;
    private final BaseAdapterDataModel<OrderHistoryDataModel> adapter;
}