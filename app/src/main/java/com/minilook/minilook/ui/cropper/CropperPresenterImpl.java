package com.minilook.minilook.ui.cropper;

import android.graphics.Bitmap;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.cropper.di.CropperArguments;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class CropperPresenterImpl extends BasePresenterImpl implements CropperPresenter {

    private final View view;
    private final File cacheFile;

    public CropperPresenterImpl(CropperArguments args) {
        view = args.getView();
        cacheFile = args.getCacheFile();
    }

    @Override public void onCreate() {
        view.clickAction();
        view.setupCropper(cacheFile);
    }

    @Override public void onDestroy() {
        deleteCacheFile();
    }

    @Override public void onBackClick() {
        view.finish();
    }

    @Override public void onRotateLeftClick() {
        view.setRotateLeft();
        view.setCropRatioReverse();
    }

    @Override public void onRotateRightClick() {
        view.setRotateRight();
        view.setCropRatioReverse();
    }

    @Override public void omCropClick() {
        view.showCropPanel();
    }

    @Override public void onCropFreeClick() {
        view.setCropRatioFree();
        view.hideCropPanel();
    }

    @Override public void onCrop11Click() {
        view.setCropRatio11();
        view.hideCropPanel();
    }

    @Override public void onCrop43Click() {
        view.setCropRatio43();
        view.hideCropPanel();
    }

    @Override public void onCrop34Click() {
        view.setCropRatio34();
        view.hideCropPanel();
    }

    @Override public void onCropWindowMove() {
        view.hideCropPanel();
    }

    @Override public void onApplyClick(Bitmap cropImage) {
        RxBus.send(new RxEventCropCompleted(cacheFile.getName(), cropImage));
        view.finish();
    }

    private void deleteCacheFile() {
        if (cacheFile != null) cacheFile.deleteOnExit();
    }

    @AllArgsConstructor @Getter public final static class RxEventCropCompleted {
        private final String fileName;
        private final Bitmap cropImage;
    }
}