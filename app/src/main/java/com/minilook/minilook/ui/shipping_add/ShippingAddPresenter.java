package com.minilook.minilook.ui.shipping_add;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ShippingAddPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSearchZipClick();

    void onNameTextChanged(String text);

    void onPhoneTextChanged(String text);

    void onAddressDetailTextChanged(String text);

    void onDefaultClick();

    void onSaveClick();

    interface View {

        void setupNameEditText();

        void setupPhoneEditText();

        void setupAddressDetailEditText();

        void checkDefault();

        void uncheckDefault();

        void enableSaveButton();

        void disableSaveButton();

        void navigateToSearchZip();
    }
}
