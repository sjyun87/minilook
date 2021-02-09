package com.minilook.minilook.ui.gallery.di;

import android.content.ContentResolver;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.ui.gallery.GalleryPresenter;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GalleryArguments {
    private final GalleryPresenter.View view;
    private final ContentResolver contentResolver;
    private final BaseAdapterDataModel<AlbumDataModel> albumAdapter;
    private final BaseAdapterDataModel<PhotoDataModel> galleryAdapter;
    private final BaseAdapterDataModel<PhotoDataModel> selectedAdapter;
    private final List<PhotoDataModel> photos;
}