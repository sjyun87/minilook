package com.minilook.minilook.ui.review_history;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.minilook.minilook.databinding.ActivityReviewHistoryBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.review_history.di.ReviewHistoryArguments;

public class ReviewHistoryActivity extends BaseActivity implements ReviewHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ActivityReviewHistoryBinding binding;
    private ReviewHistoryPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityReviewHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ReviewHistoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewHistoryArguments provideArguments() {
        return ReviewHistoryArguments.builder()
            .view(this)
            .build();
    }
}
