package com.minilook.minilook.ui.question_history;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.databinding.ActivityQuestionHistoryBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.listener.OnPositiveClickListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.question_edit.QuestionEditActivity;
import com.minilook.minilook.ui.question_history.adapter.QuestionHistoryAdapter;
import com.minilook.minilook.ui.question_history.di.QuestionHistoryArguments;

public class QuestionHistoryActivity extends BaseActivity implements QuestionHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, QuestionHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @DimenRes int dp_6 = R.dimen.dp_6;

    private ActivityQuestionHistoryBinding binding;
    private QuestionHistoryPresenter presenter;

    private final QuestionHistoryAdapter adapter = new QuestionHistoryAdapter();
    private final BaseAdapterDataView<QuestionDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityQuestionHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new QuestionHistoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionHistoryArguments provideArguments() {
        return QuestionHistoryArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        binding.rcvQuestion.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvQuestion.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_6))
            .asSpace()
            .build()
            .addTo(binding.rcvQuestion);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvQuestion.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        binding.rcvQuestion.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        binding.txtEmpty.setVisibility(View.VISIBLE);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void showQuestionDeleteDialog(int productNo, int questionNo) {
        DialogManager.showQuestionDeleteDialog(this, () -> presenter.onQuestionDelete(productNo, questionNo));
    }

    @Override public void scrollToTop() {
        binding.rcvQuestion.scrollToPosition(0);
    }

    @Override public void navigateToQuestionEdit(QuestionDataModel data) {
        QuestionEditActivity.start(this, data);
    }

    @Override public void showSecretEditDialog(QuestionDataModel data) {
        DialogManager.showQuestionSecretEditDialog(this, data.isSecret(), () -> presenter.onSecretEdit(data));
    }
}
