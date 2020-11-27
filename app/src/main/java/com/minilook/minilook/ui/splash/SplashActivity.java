package com.minilook.minilook.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.messaging.FirebaseMessaging;
import com.minilook.minilook.databinding.ActivitySplashBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.guide.GuideActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.splash.di.SplashArguments;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

    private ActivitySplashBinding binding;
    private SplashPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        return binding.getRoot();
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

    @Override public void setupLottieView() {
        binding.lottieLogo.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                presenter.onAnimationEnd();
                binding.lottieLogo.removeAnimatorListener(this);
            }
        });
    }

    @Override public void setupDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(getIntent())
            .addOnCompleteListener(this, presenter::onDynamicLink);
    }

    @Override public void setupPushToken() {
        FirebaseMessaging.getInstance()
            .getToken()
            .addOnCompleteListener(presenter::onPushToken);
    }

    @Override public void showUpdateDialog() {
        DialogManager.showUpdateDialog(this, presenter::onUpdateDialogOkClick);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void navigateToPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.format("market://details?id=%s", getPackageName())));
        startActivity(intent);
        finish();
    }

    @Override public void navigateToGuide() {
        GuideActivity.start(this);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this);
    }

    @Override public void onBackPressed() {
    }
}
