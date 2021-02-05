package com.minilook.minilook.ui.album;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import java.io.File;

public interface GalleryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onSelectAlbumClick();

    void onCancelClick();

    void onCameraPermissionGranted();

    void onCameraCallback(File file);

    void onOkClick();

    interface View {

        void setupClickAction();

        void setupGalleryRecyclerView();

        void galleryRefresh();

        void setupAlbumRecyclerView();

        void albumRefresh();

        void setupSelectPhotoRecyclerView();

        void selectedPhotoRefresh();

        void showLimitToast();

        void setTitle(String name);

        void openAlbumSelectPanel();

        void closeAlbumSelectPanel();

        void checkCameraPermission();

        void showSelectedPanel();

        void hideSelectedPanel();

        void enableApplyButton();

        void disableApplyButton();

        void navigateToCamera();

        void navigateToCropper(File file);

        void clear();

        void finish();
    }
}
