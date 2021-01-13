package com.minilook.minilook.ui.challenge_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ActivityChallengeDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.challenge_detail.adapter.ChallengeDetailImageAdapter;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.login.LoginActivity;

public class ChallengeDetailActivity extends BaseActivity implements ChallengeDetailPresenter.View {

    public static void start(Context context, int challengeNo) {
        Intent intent = new Intent(context, ChallengeDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("challengeNo", challengeNo);
        context.startActivity(intent);
    }

    @StringRes int str_date_day = R.string.challenge_detail_end_date_day_unit;
    @StringRes int str_date_time = R.string.challenge_detail_end_date_time;
    @StringRes int str_date_hour = R.string.challenge_detail_end_date_hour_unit;
    @StringRes int str_date_minute = R.string.challenge_detail_end_date_minute_unit;

    @StringRes int str_term_date = R.string.challenge_detail_term_date;
    @StringRes int str_review_date = R.string.challenge_detail_review_date;
    @StringRes int str_winner_unit = R.string.challenge_detail_winner_count;

    @StringRes int str_enter_button = R.string.challenge_detail_button_enter;
    @StringRes int str_enter_completed_button = R.string.challenge_detail_button_enter_completed;
    @StringRes int str_end_button = R.string.challenge_detail_button_end;

    @ColorRes int color_FF8A8A8A = R.color.color_FF8A8A8A;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    private ActivityChallengeDetailBinding binding;
    private ChallengeDetailPresenter presenter;

    private final ChallengeDetailImageAdapter imageAdapter = new ChallengeDetailImageAdapter();
    private final BaseAdapterDataView<String> imageAdapterView = imageAdapter;

    @Override protected View getBindingView() {
        binding = ActivityChallengeDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ChallengeDetailArguments provideArguments() {
        return ChallengeDetailArguments.builder()
            .view(this)
            .challengeNo(getIntent().getIntExtra("challengeNo", -1))
            .imageAdapter(imageAdapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.txtEnterButton.setOnClickListener(view -> presenter.onEnterClick());
    }

    @Override public void setupViewPager() {
        binding.vpImage.setAdapter(imageAdapter);
        binding.indicator.setViewPager2(binding.vpImage);
    }

    @Override public void imageRefresh() {
        imageAdapterView.refresh();
    }

    @Override public void setBrandName(String brandName) {
        binding.txtBrandName.setText(brandName);
    }

    @Override public void setProductName(String productName) {
        binding.txtProductName.setText(productName);
    }

    @Override public void showRemainTime(int day, int hour, int minute) {
        showDay(day);
        showTime(hour, minute);
        binding.txtDateEnd.setVisibility(View.GONE);
        binding.layoutRemainDatePanel.setVisibility(View.VISIBLE);
    }

    @Override public void showRemainTime(int hour, int minute) {
        hideDay();
        showTime(hour, minute);
        binding.txtDateEnd.setVisibility(View.GONE);
        binding.layoutRemainDatePanel.setVisibility(View.VISIBLE);
    }

    @Override public void showRemainTime(int minute) {
        hideDay();
        showTime(minute);
        binding.txtDateEnd.setVisibility(View.GONE);
        binding.layoutRemainDatePanel.setVisibility(View.VISIBLE);
    }

    private void showDay(int day) {
        String strDay = String.format(resources.getString(str_date_day), day);
        binding.txtDateDay.setText(strDay);
        binding.txtDateDay.setVisibility(View.VISIBLE);
    }

    private void hideDay() {
        binding.txtDateDay.setVisibility(View.GONE);
    }

    private void showTime(int hour, int minute) {
        String strHour = String.format(resources.getString(str_date_hour), hour);
        String strMinute = String.format(resources.getString(str_date_minute), minute);
        binding.txtDateTime.setText(String.format(resources.getString(str_date_time), strHour, strMinute));
    }

    private void showTime(int minute) {
        String strMinute = String.format(resources.getString(str_date_minute), minute);
        binding.txtDateTime.setText(strMinute);
    }

    private void hideTime() {
        binding.txtDateTime.setVisibility(View.GONE);
    }

    @Override public void showEndTime() {
        binding.layoutRemainDatePanel.setVisibility(View.GONE);
        binding.txtDateEnd.setVisibility(View.VISIBLE);
    }

    @Override public void showLabel() {
        binding.txtLabel.setVisibility(View.VISIBLE);
    }

    @Override public void hideLabel() {
        binding.txtLabel.setVisibility(View.GONE);
    }

    @Override public void setTermDate(String start, String end) {
        binding.txtTermDate.setText(String.format(resources.getString(str_term_date), start, end));
    }

    @Override public void setWinDate(String date) {
        binding.txtWinDate.setText(date);
    }

    @Override public void setReviewDate(String date) {
        binding.txtReviewDate.setText(String.format(resources.getString(str_review_date), date));
    }

    @Override public void setWinnerCount(int count) {
        binding.txtWinnerCount.setText(String.format(resources.getString(str_winner_unit), count));
    }

    @Override public void setDetailContent(String url) {
        Glide.with(this)
            .load(url)
            .into(binding.imgDetail);
    }

    @Override public void setChallengeContent(String url) {
        Glide.with(this)
            .load(url)
            .into(binding.imgChallenge);
    }

    @Override public void showEnterButton() {
        binding.txtEnterButton.setText(resources.getString(str_enter_button));
        binding.txtEnterButton.setBackgroundColor(resources.getColor(color_FF8140E5));
        binding.txtEnterButton.setEnabled(true);
    }

    @Override public void showEnterCompletedButton() {
        binding.txtEnterButton.setText(resources.getString(str_enter_completed_button));
        binding.txtEnterButton.setBackgroundColor(resources.getColor(color_FF8A8A8A));
        binding.txtEnterButton.setEnabled(false);
    }

    @Override public void showEndButton() {
        binding.txtEnterButton.setText(resources.getString(str_end_button));
        binding.txtEnterButton.setBackgroundColor(resources.getColor(color_FF8A8A8A));
        binding.txtEnterButton.setEnabled(false);
    }

    @Override public void navigateToChallengeEnter() {
        ////
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }
}
