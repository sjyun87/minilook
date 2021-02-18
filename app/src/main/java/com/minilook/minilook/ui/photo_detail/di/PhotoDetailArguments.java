package com.minilook.minilook.ui.photo_detail.di;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.photo_detail.PhotoDetailPresenter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PhotoDetailArguments {
    private final PhotoDetailPresenter.View view;
    private final PhotoDetailDataModel data;
    private final BaseAdapterDataModel<ImageDataModel> adapter;
}