package com.minilook.minilook.ui.cropper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;
import android.view.View;
import com.minilook.minilook.databinding.ActivityCropperBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.cropper.di.CropperArguments;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;

public class CropperActivity extends BaseActivity implements CropperPresenter.View {

    public static void start(Context context, File file) {
        Intent intent = new Intent(context, CropperActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("cache", file);
        context.startActivity(intent);
    }

    private ActivityCropperBinding binding;
    private CropperPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityCropperBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new CropperPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private CropperArguments provideArguments() {
        return CropperArguments.builder()
            .view(this)
            .cacheFile((File) getIntent().getSerializableExtra("cache"))
            .build();
    }

    @Override public void clickAction() {
        binding.imgBack.setOnClickListener(view -> presenter.onBackClick());
        binding.imgRotateLeft.setOnClickListener(view -> presenter.onRotateLeftClick());
        binding.imgRotateRight.setOnClickListener(view -> presenter.onRotateRightClick());
        binding.imgCrop.setOnClickListener(view -> presenter.omCropClick());
        binding.layoutCropFree.setOnClickListener(view -> presenter.onCropFreeClick());
        binding.layoutCrop11.setOnClickListener(view -> presenter.onCrop11Click());
        binding.layoutCrop43.setOnClickListener(view -> presenter.onCrop43Click());
        binding.layoutCrop34.setOnClickListener(view -> presenter.onCrop34Click());
        binding.txtApply.setOnClickListener(view -> {
            Bitmap cropImage = binding.imgCropper.getCroppedImage();
            presenter.onApplyClick(cropImage);
        });
    }

    @Override public void setupCropper(File image) {
        binding.imgCropper.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
        binding.imgCropper.setAutoZoomEnabled(true);
        binding.imgCropper.setShowProgressBar(true);
        binding.imgCropper.setImageUriAsync(Uri.fromFile(image));
        binding.imgCropper.setOnSetCropOverlayMovedListener(rect -> presenter.onCropWindowMove());
    }

    @Override public void setRotateLeft() {
        binding.imgCropper.setRotatedDegrees(binding.imgCropper.getRotatedDegrees() - 90);
    }

    @Override public void setRotateRight() {
        binding.imgCropper.setRotatedDegrees(binding.imgCropper.getRotatedDegrees() + 90);
    }

    @Override public void setCropRatioReverse() {
        Pair<Integer, Integer> pair = binding.imgCropper.getAspectRatio();
        binding.imgCropper.setAspectRatio(pair.second, pair.first);
    }

    @Override public void showCropPanel() {
        binding.layoutCropPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideCropPanel() {
        binding.layoutCropPanel.setVisibility(View.GONE);
    }

    @Override public void setCropRatioFree() {
        binding.imgCropper.setFixedAspectRatio(false);
    }

    @Override public void setCropRatio11() {
        binding.imgCropper.setAspectRatio(1, 1);
        binding.imgCropper.setFixedAspectRatio(true);
    }

    @Override public void setCropRatio43() {
        binding.imgCropper.setAspectRatio(4, 3);
        binding.imgCropper.setFixedAspectRatio(true);
    }

    @Override public void setCropRatio34() {
        binding.imgCropper.setAspectRatio(3, 4);
        binding.imgCropper.setFixedAspectRatio(true);
    }
}
