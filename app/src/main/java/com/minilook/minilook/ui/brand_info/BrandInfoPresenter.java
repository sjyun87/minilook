package com.minilook.minilook.ui.brand_info;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BrandInfoPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupCStime(String time);

        void setupCStel(String tel);

        void hideCSsnsPanel();

        void setupCSsns(String sns);

        void setupCSemail(String email);

        void setupGuide(String guide);
    }
}
