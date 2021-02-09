package com.minilook.minilook.ui.review_history.view.written.di;

import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.review_history.view.written.WrittenReviewPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewWrittenArguments {
    private final WrittenReviewPresenter.View view;
    private final BaseAdapterDataModel<ReviewDataModel> adapter;
}