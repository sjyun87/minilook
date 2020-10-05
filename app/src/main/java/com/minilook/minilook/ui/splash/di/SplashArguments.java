package com.minilook.minilook.ui.splash.di;

import com.minilook.minilook.ui.splash.SplashPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SplashArguments {
    private final SplashPresenter.View view;
}