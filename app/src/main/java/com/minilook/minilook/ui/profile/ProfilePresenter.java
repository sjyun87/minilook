package com.minilook.minilook.ui.profile;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ProfilePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTextChanged(String text);

    void onNickClearClick();

    void onNickSaveClick();

    void onPhoneEditClick();

    void onShippingManagementClick();

    interface View {

        void setupEditText();

        void setupNick(String text);

        void showCheckMessage(String message);

        void showCheckErrorMessage();

        void hideCheckMessage();

        void setupPhone(String text);

        void setupEmail(String text);

        void showShippingPanel();

        void hideShippingPanel();

        void setupShippingName(String text);

        void setupShippingPhone(String text);

        void setupShippingAddress(String zipcode, String address, String address_detil);

        void showEmptyShippingText();

        void hideEmptyShippingText();

        void enableNickSaveButton();

        void disableNickSaveButton();

        void showNickClearButton();

        void hideNickClearButton();

        void hideKeyboard();

        void showUpdateCompletedToast();

        void navigateToWebView(String url);

        void navigateToShipping();
    }
}
