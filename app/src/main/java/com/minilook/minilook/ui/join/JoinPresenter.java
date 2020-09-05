package com.minilook.minilook.ui.join;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface JoinPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onFullAgreeClick();

    void onTermsOfUseClick();

    void onTermsOfUseDetailClick();

    void onPrivacyPolicyClick();

    void onPrivacyPolicyDetailClick();

    void onCommercialClick();

    void onJoinClick();

    void onBackClick();

    void onJoinCompletedDialogCloseClick();

    void onJoinCancelDialogCancelClick();

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

        void showSignInCancelDialog();

        void showJoinLimitedDialog();

        void showJoinCompletedDialog();

        void navigateToMain();

        void navigateToWebView(String url);

        void finish();
    }
}
