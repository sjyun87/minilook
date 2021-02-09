package com.minilook.minilook.ui.review_history;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ActivityReviewHistoryBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.review_history.adapter.ReviewHistoryPagerAdapter;
import com.minilook.minilook.ui.review_history.di.ReviewHistoryArguments;

public class ReviewHistoryActivity extends BaseActivity implements ReviewHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @DrawableRes int bg_left_off = R.drawable.bg_review_history_left_off;
    @DrawableRes int bg_left_on = R.drawable.bg_review_history_left_on;
    @DrawableRes int bg_right_off = R.drawable.bg_review_history_right_off;
    @DrawableRes int bg_right_on = R.drawable.bg_review_history_right_on;

    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;
    @ColorRes int color_FFFFFFFF = R.color.color_FFFFFFFF;

    private ActivityReviewHistoryBinding binding;
    private ReviewHistoryPresenter presenter;

    private ReviewHistoryPagerAdapter adapter;

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

    @Override public void setupClickAction() {
        binding.txtWrittenReview.setOnClickListener(view -> presenter.onWrittenReviewClick());
        binding.txtWritableReview.setOnClickListener(view -> presenter.onWritableReviewClick());
    }

    @Override public void setupViewPager() {
        adapter = new ReviewHistoryPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.vpHistory.setAdapter(adapter);
        binding.vpHistory.setCurrentItem(0);
        binding.vpHistory.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }
        });
    }

    @Override public void setCurrentPage(int page) {
        binding.vpHistory.setCurrentItem(page, true);
    }

    @Override public void enableWrittenReviewButton() {
        binding.txtWrittenReview.setTextColor(resources.getColor(color_FFFFFFFF));
        binding.txtWrittenReview.setBackground(resources.getDrawable(bg_left_on));
    }

    @Override public void disableWrittenReviewButton() {
        binding.txtWrittenReview.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtWrittenReview.setBackground(resources.getDrawable(bg_left_off));
    }

    @Override public void enableWritableReviewButton() {
        binding.txtWritableReview.setTextColor(resources.getColor(color_FFFFFFFF));
        binding.txtWritableReview.setBackground(resources.getDrawable(bg_right_on));
    }

    @Override public void disableWritableReviewButton() {
        binding.txtWritableReview.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtWritableReview.setBackground(resources.getDrawable(bg_right_off));
    }
}
