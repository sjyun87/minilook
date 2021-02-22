package com.minilook.minilook.ui.question_edit;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import java.util.List;

public interface QuestionEditPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTextChanged(String text);

    void onTypeBoxClick();

    void onTypeSelected(String data);

    void onSecretClick();

    void onApplyClick();

    void onStoragePermissionGranted();

    interface View {

        void setupClickAction();

        void setupTypeRecyclerView();

        void setupPhotoRecyclerView();

        void photoRefresh();

        void showTypeBox();

        void hideTypeBox();

        void setSelectedType(String type);

        void setupQuestionEditText();

        void setQuestion(String text);

        void setSelectedPhotoCount(int size);

        void checkSecretCheckBox();

        void uncheckSecretCheckBox();

        void enableApplyButton();

        void disableApplyButton();

        void setupGuideText();

        void showQuestionWriteToast();

        void showLoadingView();

        void hideLoadingView();

        void showErrorDialog();

        void checkStoragePermission();

        void navigateToGallery(List<PhotoDataModel> photos);

        void finish();
    }
}
