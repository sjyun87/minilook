package com.minilook.minilook.ui.challenge_enter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ActivityChallengeEnterBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.challenge_enter.di.ChallengeEnterArguments;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.verify.VerifyActivity;
import com.minilook.minilook.ui.webview.WebViewActivity;

public class ChallengeEnterActivity extends BaseActivity implements ChallengeEnterPresenter.View {

    public static void start(Context context, int challengeNo) {
        Intent intent = new Intent(context, ChallengeEnterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("challengeNo", challengeNo);
        context.startActivity(intent);
    }

    @StringRes int str_phone_edit = R.string.challenge_enter_tel_edit;

    @ColorRes int color_FFF5F5F5 = R.color.color_FFF5F5F5;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    @DrawableRes int img_checkbox_off = R.drawable.ic_checkbox_circle_off;
    @DrawableRes int img_checkbox_on = R.drawable.ic_checkbox_circle_on;

    private ActivityChallengeEnterBinding binding;
    private ChallengeEnterPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityChallengeEnterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeEnterPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ChallengeEnterArguments provideArguments() {
        return ChallengeEnterArguments.builder()
            .view(this)
            .challengeNo(getIntent().getIntExtra("challengeNo", -1))
            .build();
    }

    @Override public void setupClickAction() {
        binding.txtCertify.setOnClickListener(view -> presenter.onCertifyClick());
        binding.imgCheckbox.setOnClickListener(view -> presenter.onTermsAgree());
        binding.txtNoticeMore.setOnClickListener(view -> presenter.onNoticeMoreClick());
        binding.txtApply.setOnClickListener(view -> presenter.onApplyClick());
    }

    @Override public void setupInstargramEditText() {
        binding.editInstagram.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onInstagramTextChanged(s.toString().trim());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setupBlogEditText() {
        binding.editBlog.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onBlogTextChanged(s.toString().trim());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setPhoneNumber(String phone) {
        binding.txtTel.setText(phone);
        binding.txtCertify.setText(resources.getString(str_phone_edit));
    }

    @Override public void checkTerms() {
        binding.imgCheckbox.setImageResource(img_checkbox_on);
    }

    @Override public void uncheckTerms() {
        binding.imgCheckbox.setImageResource(img_checkbox_off);
    }

    @Override public void enableApplyButton() {
        binding.txtApply.setBackgroundColor(resources.getColor(color_FF8140E5));
        binding.txtApply.setEnabled(true);
    }

    @Override public void disableApplyButton() {
        binding.txtApply.setBackgroundColor(resources.getColor(color_FFF5F5F5));
        binding.txtApply.setEnabled(false);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void showFinishDialog() {
        DialogManager.showChallengeEnterCompletedDialog(this,
            presenter::onDialogCloseClick);
    }

    @Override public void showFinishAndMarketingInfoDialog() {
        DialogManager.showChallengeEnterCompletedNMarketingDialog(this,
            presenter::onDialogAgreeClick,
            presenter::onDialogLaterClick);
    }

    @Override public void navigateToVerify() {
        VerifyActivity.start(this);
    }

    @Override public void navigateToWebView(String url) {
        WebViewActivity.start(this, url);
    }
}
