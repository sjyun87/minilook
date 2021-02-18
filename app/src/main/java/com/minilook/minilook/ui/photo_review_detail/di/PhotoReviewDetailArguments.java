package com.minilook.minilook.ui.photo_review_detail.di;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.photo_review_detail.PhotoReviewDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PhotoReviewDetailArguments {
    private final PhotoReviewDetailPresenter.View view;
    private final int productNo;
    private final PhotoDetailDataModel data;
    private final BaseAdapterDataModel<ImageDataModel> adapter;
}