package com.minilook.minilook.ui.signin;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SignInPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onFullAgreeClick();

    void onTermsOfUseClick();

    void onPrivacyPolicyClick();

    void onCommercialClick();

    void onJoinClick();

    void onBackClick();

    void onSignInCompletedDialogCloseClick();

    interface View {

        void setupChainSNS(String type);

        void setupEmail(String email);

        void showVerifyCompleteButton();

        void checkFullAgree();

        void uncheckFullAgree();

        void checkTermsOfUse();

        void uncheckTermsOfUse();

        void checkPrivacyPolicy();

        void uncheckPrivacyPolicy();

        void checkCommercialInfoCheck();

        void uncheckCommercialInfoCheck();

        void enableJoinButton();

        void disableJoinButton();

        void showResetJoinDialog();

        void showLimitJoinDialog();

        void showSignCompletedDialog();

        void finish();
    }
}
