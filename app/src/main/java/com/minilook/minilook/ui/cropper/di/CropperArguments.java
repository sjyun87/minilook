package com.minilook.minilook.ui.cropper.di;

import com.minilook.minilook.ui.cropper.CropperPresenter;
import java.io.File;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CropperArguments {
    private final CropperPresenter.View view;
    private final File cacheFile;
}