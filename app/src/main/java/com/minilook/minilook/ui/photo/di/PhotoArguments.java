package com.minilook.minilook.ui.photo.di;

import com.minilook.minilook.data.model.image.ImageDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.photo.PhotoPresenter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PhotoArguments {
    private final PhotoPresenter.View view;
    private final List<ImageDataModel> photos;
    private final int position;
    private final BaseAdapterDataModel<ImageDataModel> adapter;
}