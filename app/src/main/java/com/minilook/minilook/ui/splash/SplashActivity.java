package com.minilook.minilook.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minilook.minilook.databinding.ActivitySplashBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.guide.GuideActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.splash.di.SplashArguments;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

    private LottieAnimationView lottieView;

    private SplashPresenter presenter;

    @Override protected View getBindingView() {
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        lottieView = binding.imgLogoSymbol;
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
        lottieView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                presenter.onAnimationEnd();
                lottieView.removeAllAnimatorListeners();
            }
        });
    }

    @Override public void checkDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(getIntent())
            .addOnCompleteListener(this, presenter::onDynamicLinkCheckComplete);
    }

    @Override public void showUpdateDialog() {
        DialogManager.showUpdateDialog(this, presenter::onUpdateDialogOkClick, presenter::onUpdateDialogCancelClick);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this, presenter::onErrorDialogOkClick);
    }

    @Override public void navigateToPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
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
