package com.minilook.minilook.ui.photo;

import com.minilook.minilook.data.model.image.ImageDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.photo.di.PhotoArguments;
import java.util.List;

public class PhotoPresenterImpl extends BasePresenterImpl implements PhotoPresenter {

    private final View view;
    private final List<ImageDataModel> photos;
    private final int position;
    private final BaseAdapterDataModel<ImageDataModel> adapter;

    public PhotoPresenterImpl(PhotoArguments args) {
        view = args.getView();
        photos = args.getPhotos();
        position = args.getPosition();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupViewPager();

        setData();
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onPageSelected(int position) {
        setSelectedPage(position);
    }

    private void setData() {
        adapter.set(photos);
        view.refresh();

        view.setCurrentItem(position);
        setSelectedPage(position);
    }

    private void setSelectedPage(int index) {
        view.setSelectedPage(index + 1, photos.size());
    }
}