package com.minilook.minilook.ui.setting;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SettingPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLogoutClick();

    void onInfoNotifyChecked(boolean isChecked);

    void onMarketingChecked(boolean isChecked);

    interface View {

        void setupInfoSwitchButton();

        void setupMarketingSwitchButton();
    }
}
