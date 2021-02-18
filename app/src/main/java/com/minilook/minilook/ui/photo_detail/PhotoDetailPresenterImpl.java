package com.minilook.minilook.ui.photo_detail;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.photo_detail.di.PhotoDetailArguments;

public class PhotoDetailPresenterImpl extends BasePresenterImpl implements PhotoDetailPresenter {

    private final View view;
    private final PhotoDetailDataModel data;
    private final BaseAdapterDataModel<ImageDataModel> adapter;

    private boolean isExpand = false;

    public PhotoDetailPresenterImpl(PhotoDetailArguments args) {
        view = args.getView();
        data = args.getData();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupViewPager();

        adapter.set(data.getPhotos());
        view.refresh();

        view.setCurrentItem(data.getPosition());
        setSelectedPage(data.getPosition());

        view.setContents(data.getContents());
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onPageSelected(int position) {
        setSelectedPage(position);
    }

    @Override public void onMoreClick() {
        isExpand = !isExpand;
        if (isExpand) {
            view.setExpandButton();
        } else {
            view.collapseButton();
        }
    }

    private void setSelectedPage(int index) {
        view.setSelectedPage(index + 1, data.getPhotos().size());
    }
}