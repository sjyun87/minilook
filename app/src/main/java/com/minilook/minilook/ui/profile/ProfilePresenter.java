package com.minilook.minilook.ui.profile;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ProfilePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupNick(String text);

        void setupPhone(String text);

        void setupEmail(String text);

        void showShippingPanel();

        void hideShippingPanel();

        void setupShippingName(String text);

        void setupShippingPhone(String text);

        void setupShippingAddress(String zipcode, String address, String address_detil);

        void showEmptyShippingText();

        void hideEmptyShippingText();

        void showShippingAddButton();

        void hideShippingAddButton();

        void showShippingEditButton();

        void hideShippingEditButton();
    }
}
