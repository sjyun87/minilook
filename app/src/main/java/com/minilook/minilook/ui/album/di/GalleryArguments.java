package com.minilook.minilook.ui.album.di;

import android.content.ContentResolver;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.data.model.gallery.GalleryDataModel;
import com.minilook.minilook.ui.album.GalleryPresenter;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GalleryArguments {
    private final GalleryPresenter.View view;
    private final ContentResolver contentResolver;
    private final BaseAdapterDataModel<AlbumDataModel> albumAdapter;
    private final BaseAdapterDataModel<GalleryDataModel> galleryAdapter;
    private final BaseAdapterDataModel<GalleryDataModel> selectedAdapter;
}