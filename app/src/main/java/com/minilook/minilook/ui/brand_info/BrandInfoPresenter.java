package com.minilook.minilook.ui.brand_info;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BrandInfoPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setCStime(String time);

        void setCStel(String tel);

        void hideCSsnsPanel();

        void setCSsns(String sns);

        void setCSemail(String email);

        void setGuide(String guide);

        void showErrorDialog();
    }
}
