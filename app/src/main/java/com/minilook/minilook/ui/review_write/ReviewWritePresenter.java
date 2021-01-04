package com.minilook.minilook.ui.review_write;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ReviewWritePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSatisfactionClick(int code);

    void onSizeClick(int code);

    void onGenderClick(int code);

    void onAgeInputClick();

    void onPhotoAddClick();

    void onStoragePermissionGranted();




    void onTextChanged(String text);

    void onApplyClick();

    interface View {

        void setupClickAction();

        void setOrderNo(String orderNo);

        void setOrderDate(String orderDate);

        void setThumb(String url);

        void setBrandName(String name);

        void setProductName(String name);

        void setOption(String color, String size);

        void selectGoodButton();

        void unselectGoodButton();

        void selectNormalButton();

        void unselectNormalButton();

        void selectBadButton();

        void unselectBadButton();

        void selectVeryBigButton();

        void selectLittleBigButton();

        void selectPerfectlyButton();

        void selectLittleSmallButton();

        void selectVerySmallButton();

        void unselectVeryBigButton();

        void unselectLittleBigButton();

        void unselectPerfectlyButton();

        void unselectLittleSmallButton();

        void unselectVerySmallButton();

        void selectMaleButton();

        void unselectMaleButton();

        void selectFemaleButton();

        void unselectFemaleButton();

        void showAgePicker();

        void hideAgePicker();

        void checkStoragePermission();






        void setupReviewEditText();

        void enableApplyButton();

        void disableApplyButton();

        void showReviewWriteToast();

        void navigateToAlbum();

        void finish();
    }
}
