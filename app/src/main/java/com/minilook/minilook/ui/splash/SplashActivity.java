package com.minilook.minilook.ui.splash;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.splash.di.SplashArguments;
import com.minilook.minilook.util.HashKeyUtil;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

    private SplashPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override protected void createPresenter() {
        presenter = new SplashPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SplashArguments provideArguments() {
        return SplashArguments.builder()
            .view(this)
            .build();
    }

    @Override public void navigateToMain() {
        MainActivity.start(this);
    }

    @Override public void onBackPressed() {
    }
}
