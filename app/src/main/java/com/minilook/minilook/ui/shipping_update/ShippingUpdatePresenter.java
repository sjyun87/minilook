package com.minilook.minilook.ui.shipping_update;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ShippingUpdatePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSearchZipClick();

    void onNameTextChanged(String text);

    void onPhoneTextChanged(String text);

    void onAddressDetailTextChanged(String text);

    void onDefaultClick();

    void onSaveClick();

    interface View {

        void setupShippingEditTitleBar();

        void setupNameEditText();

        void setupPhoneEditText();

        void setupAddressDetailEditText();

        void checkDefault();

        void uncheckDefault();

        void enableSaveButton();

        void disableSaveButton();

        void setupName(String text);

        void setupPhone(String text);

        void setupZip(String text);

        void setupAddress(String text);

        void setupAddressDetail(String text);

        void navigateToSearchZip();

        void finish();
    }
}
