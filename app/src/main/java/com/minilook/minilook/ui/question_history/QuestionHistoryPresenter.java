package com.minilook.minilook.ui.question_history;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.question.QuestionDataModel;

public interface QuestionHistoryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onQuestionDelete(int productNo, int questionNo);

    void onSecretEdit(QuestionDataModel data);

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void showErrorDialog();

        void showQuestionDeleteDialog(int productNo, int questionNo);

        void scrollToTop();

        void navigateToQuestionEdit(QuestionDataModel data);

        void showSecretEditDialog(QuestionDataModel data);
    }
}
