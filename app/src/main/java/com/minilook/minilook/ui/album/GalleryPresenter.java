package com.minilook.minilook.ui.album;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface GalleryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSelectAlbumClick();

    void onCancelClick();

    void onCameraPermissionGranted();

    void onCameraCallback();

    interface View {

        void setupClickAction();

        void setupGalleryRecyclerView();

        void galleryRefresh();

        void setupAlbumRecyclerView();

        void albumRefresh();

        void setTitle(String name);

        void openAlbumSelectPanel();

        void closeAlbumSelectPanel();

        void checkCameraPermission();

        void navigateToCamera();

        void finish();
    }
}
