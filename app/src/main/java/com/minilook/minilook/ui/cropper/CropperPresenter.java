package com.minilook.minilook.ui.cropper;

import android.graphics.Bitmap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import java.io.File;

public interface CropperPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onBackClick();

    void onRotateLeftClick();

    void onRotateRightClick();

    void omCropClick();

    void onCropFreeClick();

    void onCrop11Click();

    void onCrop43Click();

    void onCrop34Click();

    void onCropWindowMove();

    void onApplyClick(Bitmap cropImage);

    interface View {

        void clickAction();

        void setupCropper(File image);

        void setRotateLeft();

        void setRotateRight();

        void setCropRatioReverse();

        void showCropPanel();

        void hideCropPanel();

        void setCropRatioFree();

        void setCropRatio11();

        void setCropRatio43();

        void setCropRatio34();

        void finish();
    }
}
