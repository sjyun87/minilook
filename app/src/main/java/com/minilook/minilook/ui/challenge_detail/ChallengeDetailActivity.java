package com.minilook.minilook.ui.challenge_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.ChallengeType;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.ActivityChallengeDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.challenge_detail.adapter.ChallengeDetailImageAdapter;
import com.minilook.minilook.ui.challenge_detail.adapter.ChallengeRelationProductAdapter;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import com.minilook.minilook.ui.challenge_enter.ChallengeEnterActivity;
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

    @StringRes int str_open_label = R.string.challenge_detail_open_label;
    @StringRes int str_coming_label = R.string.challenge_detail_coming_label;
    @StringRes int str_end_time = R.string.challenge_detail_end;

    @StringRes int str_enter_button = R.string.challenge_detail_button_enter;
    @StringRes int str_enter_completed_button = R.string.challenge_detail_button_enter_completed;
    @StringRes int str_end_button = R.string.challenge_detail_button_end;
    @StringRes int str_coming_button = R.string.challenge_detail_button_coming;

    @ColorRes int color_FF8A8A8A = R.color.color_FF8A8A8A;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    private ActivityChallengeDetailBinding binding;
    private ChallengeDetailPresenter presenter;

    private final ChallengeDetailImageAdapter imageAdapter = new ChallengeDetailImageAdapter();
    private final BaseAdapterDataView<String> imageAdapterView = imageAdapter;
    private final ChallengeRelationProductAdapter relationProductAdapter = new ChallengeRelationProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> relationProductAdapterView = relationProductAdapter;

    private final Handler handler = new Handler(Looper.getMainLooper());

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
            .relationProductAdapter(relationProductAdapter)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void onLogout() {
        presenter.onLogout();
    }

    @Override public void setupClickAction() {
        binding.txtEnterButton.setOnClickListener(view -> presenter.onEnterClick());
        binding.imgShare.setOnClickListener(view -> presenter.onShareClick());
    }

    @Override public void setupImageViewPager() {
        binding.vpImage.setAdapter(imageAdapter);
        binding.indicator.setViewPager2(binding.vpImage);
    }

    @Override public void imageRefresh() {
        imageAdapterView.refresh();
    }

    @Override public void setupRelationProductViewPager() {
        binding.vpRelationProduct.setAdapter(relationProductAdapter);
    }

    @Override public void registPageChangeCallback() {
        binding.vpRelationProduct.registerOnPageChangeCallback(callback);
    }

    @Override public void removePageChangeCallback() {
        binding.vpRelationProduct.unregisterOnPageChangeCallback(callback);
    }

    @Override public void relationProductRefresh() {
        relationProductAdapterView.refresh();
    }

    @Override public void setRelationUserInputEnabled(boolean enable) {
        binding.vpRelationProduct.setUserInputEnabled(enable);
    }

    @Override public void startAutoSlide() {
        if (relationProductAdapter.getRealItemCount() > 1) {
            handler.postDelayed(nextPageRunnable, 3000);
        }
    }

    @Override public void cancelAutoSlide() {
        if (relationProductAdapter.getRealItemCount() > 1) {
            handler.removeCallbacks(nextPageRunnable);
        }
    }

    @Override public void setBrandName(String brandName) {
        binding.txtBrandName.setText(brandName);
    }

    @Override public void setProductName(String productName) {
        binding.txtProductName.setText(productName);
    }

    @Override public void showRemainTime(int day, int hour, int minute) {
        setDay(day);
        setTime(hour, minute);
        binding.txtEndTime.setVisibility(View.GONE);
        binding.layoutRemainDatePanel.setVisibility(View.VISIBLE);
    }

    private void setDay(int day) {
        if (day > 0) {
            String strDay = String.format(resources.getString(str_date_day), day);
            binding.txtDateDay.setText(strDay);
            binding.txtDateDay.setVisibility(View.VISIBLE);
        } else {
            binding.txtDateDay.setVisibility(View.GONE);
        }
    }

    private void setTime(int hour, int minute) {
        if (hour > 0) {
            String strHour = String.format(resources.getString(str_date_hour), hour);
            String strMinute = String.format(resources.getString(str_date_minute), minute);
            binding.txtDateTime.setText(String.format(resources.getString(str_date_time), strHour, strMinute));
        } else {
            String strMinute = String.format(resources.getString(str_date_minute), minute);
            binding.txtDateTime.setText(strMinute);
        }
    }

    @Override public void showEndTime() {
        binding.layoutRemainDatePanel.setVisibility(View.GONE);
        binding.txtEndTime.setText(resources.getString(str_end_time));
        binding.txtEndTime.setVisibility(View.VISIBLE);
    }

    @Override public void setLabel(int status) {
        switch (ChallengeType.toType(status)) {
            case OPEN:
                binding.txtLabel.setText(resources.getString(str_open_label));
                binding.txtLabel.setVisibility(View.VISIBLE);
                break;
            case COMING:
                binding.txtLabel.setText(resources.getString(str_coming_label));
                binding.txtLabel.setVisibility(View.VISIBLE);
                break;
            case END:
                binding.txtLabel.setVisibility(View.GONE);
                break;
        }
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
            .apply(new RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL))
            .into(binding.imgDetail);
    }

    @Override public void setChallengeContent(String url) {
        Glide.with(this)
            .load(url)
            .apply(new RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL))
            .into(binding.imgChallenge);
    }

    @Override public void showEnterButton() {
        binding.txtEnterButton.setText(resources.getString(str_enter_button));
        enableEnterButton();
    }

    @Override public void showEnterCompletedButton() {
        binding.txtEnterButton.setText(resources.getString(str_enter_completed_button));
        disableEnterButton();
    }

    @Override public void showEndButton() {
        binding.txtEnterButton.setText(resources.getString(str_end_button));
        disableEnterButton();
    }

    @Override public void showComingButton() {
        binding.txtEnterButton.setText(resources.getString(str_coming_button));
        disableEnterButton();
    }

    @Override public void enableEnterButton() {
        binding.txtEnterButton.setBackgroundColor(resources.getColor(color_FF8140E5));
        binding.txtEnterButton.setEnabled(true);
    }

    @Override public void disableEnterButton() {
        binding.txtEnterButton.setBackgroundColor(resources.getColor(color_FF8A8A8A));
        binding.txtEnterButton.setEnabled(false);
    }

    @Override public void showRelationProductPanel() {
        binding.layoutRelationPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideRelationProductPanel() {
        binding.layoutRelationPanel.setVisibility(View.GONE);
    }

    @Override public void scrollToTop() {
        binding.scrollView.fullScroll(View.FOCUS_UP);
    }

    @Override public void sendDynamicLink(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void navigateToChallengeEnter(int challengeNo) {
        ChallengeEnterActivity.start(this, challengeNo);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void clear() {
        binding.txtEnterButton.setOnClickListener(null);
        binding.imgShare.setOnClickListener(null);
        binding.vpImage.setAdapter(null);
    }

    private final Runnable nextPageRunnable = new Runnable() {
        @Override
        public void run() {
            if (binding.vpRelationProduct.getCurrentItem() != relationProductAdapter.getSize() - 1) {
                binding.vpRelationProduct.setCurrentItem(binding.vpRelationProduct.getCurrentItem() + 1, true);
            } else {
                binding.vpRelationProduct.setCurrentItem(0, false);
            }
        }
    };

    private final ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageSelected(int position) {
            cancelAutoSlide();
            startAutoSlide();
        }

        @Override public void onPageScrollStateChanged(int state) {
            if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                cancelAutoSlide();
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                startAutoSlide();
            }
        }
    };
}
