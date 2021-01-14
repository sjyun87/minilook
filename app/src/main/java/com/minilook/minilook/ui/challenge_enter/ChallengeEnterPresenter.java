package com.minilook.minilook.ui.challenge_enter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengeEnterPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onCertifyClick();

    void onInstagramTextChanged(String text);

    void onBlogTextChanged(String text);

    void onTermsAgree();

    void onNoticeMoreClick();

    void onApplyClick();

    void onDialogCloseClick();

    void onDialogLaterClick();

    void onDialogAgreeClick();

    interface View {

        void setupClickAction();

        void setupInstargramEditText();

        void setupBlogEditText();

        void setPhoneNumber(String phone);

        void checkTerms();

        void uncheckTerms();

        void enableApplyButton();

        void disableApplyButton();

        void showErrorDialog();

        void showFinishDialog();

        void showFinishAndMarketingInfoDialog();

        void navigateToVerify();

        void navigateToWebView(String url);

        void finish();
    }
}
