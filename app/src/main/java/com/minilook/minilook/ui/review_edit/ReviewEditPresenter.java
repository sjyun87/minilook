package com.minilook.minilook.ui.review_edit;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import java.util.List;

public interface ReviewEditPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSatisfactionClick(String code);

    void onSizeClick(String code);

    void onGenderClick(String code);

    void onAgeInputClick();

    void onHeightInputClick();

    void onWeightInputClick();

    void onPhotoAddClick();

    void onStoragePermissionGranted();

    void onAgePick(int age);

    void onHeightPick(int height);

    void onWeightPick(int weight);

    void onTextChanged(String text);

    void onApplyClick();

    void onReviewCompletedDialogOk();

    interface View {

        void setupClickAction();

        void setupAgePicker(List<Integer> ageData);

        void setAgePicker(int age);

        void showAgePicker();

        void hideAgePicker();

        void setupHeightPicker(List<Integer> heightData);

        void setHeightPicker(int height);

        void showHeightPicker();

        void hideHeightPicker();

        void setupWeightPicker(List<Integer> weightData);

        void setWeightPicker(int weight);

        void showWeightPicker();

        void hideWeightPicker();

        void setupReviewEditText();

        void setupRecyclerView();

        void refresh();

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

        void setAge(int age);

        void setHeight(int height);

        void setWeight(int weight);

        void checkStoragePermission();

        void setSelectedPhotoCount(int size);

        void showPhotoPanel();

        void hidePhotoPanel();

        void enableApplyButton();

        void disableApplyButton();

        void showLoadingView();

        void hideLoadingView();

        void showErrorDialog();

        void showReviewCompletedDialog(boolean isPhotoReview, int point);

        void navigateToGallery(List<PhotoDataModel> photos);

        void finish();
    }
}
