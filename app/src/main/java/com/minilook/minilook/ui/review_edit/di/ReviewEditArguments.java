package com.minilook.minilook.ui.review_edit.di;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.review_edit.ReviewEditPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ReviewEditArguments {
    private final ReviewEditPresenter.View view;
    private final ReviewDataModel data;
    private final BaseAdapterDataModel<PhotoDataModel> adapter;
    private final BaseAdapterDataModel<ImageDataModel> selectPhotoAdapter;
}