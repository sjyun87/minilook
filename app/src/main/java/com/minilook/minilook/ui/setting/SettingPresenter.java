package com.minilook.minilook.ui.setting;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SettingPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLogin();

    void onLogout();

    void onLoginClick();

    void onLogoutClick();

    void onLeaveClick();

    void onOrderInfoChecked(boolean isChecked);

    void onMarketingInfoChecked(boolean isChecked);

    void onTermsOfUseClick();

    void onPrivacyPolicyClick();

    void onAppQuestionClick();

    interface View {

        void setupInfoSwitchButton();

        void setupMarketingSwitchButton();

        void showOrderInfoPanel();

        void hideOrderInfoPanel();

        void setupCurrentVersion();

        void showLoginButton();

        void hideLoginButton();

        void showLogoutButton();

        void hideLogoutButton();

        void showLeaveButton();

        void hideLeaveButton();

        void navigateToLogin();

        void navigateToLeave();

        void navigateToWebView(String url);

        void navigateToMain();

        void navigateToSendEmail();
    }
}
