package com.minilook.minilook.ui.brand;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BrandPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupBgImage(String url);

        void setupDesc(String text);

        void setupName(String text);

        void setupCsDate(String text);

        void setupCsTel(String text);

        void setupCsEmail(String text);

        void setupCsAddress(String text);
    }
}
