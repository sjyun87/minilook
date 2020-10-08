package com.minilook.minilook.ui.question_write;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface QuestionWritePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTextChanged(String text);

    void onTypeBoxClick();

    void onTypeSelected(String data);

    void onSecretClick();

    void onApplyClick();

    interface View {

        void setupRecyclerView();

        void showTypeBox();

        void hideTypeBox();

        void setSelectedType(String type);

        void setupQuestionEditText();

        void checkSecretCheckBox();

        void uncheckSecretCheckBox();

        void enableApplyButton();

        void disableApplyButton();

        void showQuestionWriteToast();

        void finish();
    }
}
