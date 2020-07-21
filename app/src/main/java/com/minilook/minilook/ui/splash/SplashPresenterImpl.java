package com.minilook.minilook.ui.splash;

import android.os.Handler;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.splash.di.SplashArguments;

public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private final View view;

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

        //Handler handler = new Handler();
        //handler.postDelayed(() -> {
        //    view.navigateToMain();
        //    view.finish();
        //}, 3000);
    }
}