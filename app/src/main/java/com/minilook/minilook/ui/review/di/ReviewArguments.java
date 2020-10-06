package com.minilook.minilook.ui.review.di;

import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.review.ReviewPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewArguments {
    private final ReviewPresenter.View view;
    private final int productNo;
    private final BaseAdapterDataModel<ReviewDataModel> adapter;
}