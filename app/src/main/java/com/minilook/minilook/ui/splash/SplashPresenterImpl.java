package com.minilook.minilook.ui.splash;

import android.os.Handler;
import com.minilook.minilook.App;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.splash.di.SplashArguments;

public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private static final int TIME_ANIMATION = 2000;

    private final View view;

    private boolean isAnimationEnd = false;
    private boolean isLoginChecked = false;

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        checkAppVersion();
    }

    private void checkAppVersion() {
        appStart();
    }

    private void appStart() {
        checkAnimation();
        checkLogin();
    }

    private void checkAnimation() {
        new Handler().postDelayed(() -> {
            isAnimationEnd = true;
            checkToDo();
        }, TIME_ANIMATION);
    }

    private void checkLogin() {
        int userId = App.getInstance().getUserId();
        if (userId == -1) {
            App.getInstance().setLogin(false);
        } else {
            App.getInstance().setLogin(true);
        }
        isLoginChecked = true;
        checkToDo();
    }

    private void checkToDo() {
        if (isLoginChecked && isAnimationEnd) navigateToMain();
    }

    private void navigateToMain() {
        view.navigateToMain();
    }
}