package com.minilook.minilook.ui.brand_info;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BrandInfoPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupCStime(String text);

        void setupCStel(String text);

        void hideCSsnsPanel();

        void setupCSsns(String text);

        void setupCSemail(String text);

        void setupGuide(String text);
    }
}
