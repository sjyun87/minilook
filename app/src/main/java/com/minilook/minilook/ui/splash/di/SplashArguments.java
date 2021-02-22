package com.minilook.minilook.ui.splash.di;

import android.content.Intent;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.minilook.minilook.ui.splash.SplashPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SplashArguments {
    private final SplashPresenter.View view;
    private final Intent intent;
    private final AppUpdateManager updateManager;
}