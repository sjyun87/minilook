package com.minilook.minilook.ui.question_write;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import java.util.List;

public interface QuestionWritePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTextChanged(String text);

    void onTypeBoxClick();

    void onTypeSelected(String data);

    void onSecretClick();

    void onApplyClick();

    interface View {

        void setupClickAction();

        void setupTypeRecyclerView();

        void setupPhotoRecyclerView();

        void photoRefresh();

        void showTypeBox();

        void hideTypeBox();

        void setSelectedType(String type);

        void setupQuestionEditText();

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

        void navigateToGallery(List<PhotoDataModel> photos);

        void finish();
    }
}
