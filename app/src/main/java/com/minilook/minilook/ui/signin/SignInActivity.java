package com.minilook.minilook.ui.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minilook.minilook.R;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.type.LoginType;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.LimitJoinDialog;
import com.minilook.minilook.ui.dialog.SignInCancelDialog;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.signin.di.SignInArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements SignInPresenter.View {

    public static void start(Context context, UserDataModel userData) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("userData", userData);
        context.startActivity(intent);
    }

    @BindView(R.id.img_sns_logo) ImageView snsImageView;
    @BindView(R.id.txt_chain_sns) TextView snsTextView;
    @BindView(R.id.txt_email) TextView emailTextView;
    @BindView(R.id.txt_certify) TextView verifyButton;
    @BindView(R.id.layout_certify_completed) LinearLayout verifyCompleteButton;
    @BindView(R.id.img_full_agree_check) ImageView fullAgreeCheckBox;
    @BindView(R.id.img_terms_of_use_check) ImageView termsOfUseCheckBox;
    @BindView(R.id.img_privacy_policy_check) ImageView privacyPolicyCheckBox;
    @BindView(R.id.img_commercial_info_check) ImageView commercialInfoCheckBox;
    @BindView(R.id.txt_signin) TextView joinTextView;

    @BindDrawable(R.drawable.ic_kakao) Drawable img_kakao;
    @BindDrawable(R.drawable.ic_naver) Drawable img_naver;
    @BindDrawable(R.drawable.ic_checkbox2_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox2_on) Drawable img_check_on;

    @BindString(R.string.signin_chain_kakao) String str_chain_kakao;
    @BindString(R.string.signin_chain_naver) String str_chain_naver;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    private SignInPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_sign_in;
    }

    @Override protected void createPresenter() {
        presenter = new SignInPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SignInArguments provideArguments() {
        return SignInArguments.builder()
            .view(this)
            .userData((UserDataModel) getIntent().getSerializableExtra("userData"))
            .build();
    }

    @Override public void setupChainSNS(String type) {
        if (type.equals(LoginType.KAKAO.getValue())) {
            snsImageView.setImageDrawable(img_kakao);
            snsTextView.setText(str_chain_kakao);
        } else if (type.equals(LoginType.NAVER.getValue())) {
            snsImageView.setImageDrawable(img_naver);
            snsTextView.setText(str_chain_naver);
        }
    }

    @Override public void setupEmail(String email) {
        emailTextView.setText(email);
    }

    @Override public void showResetJoinDialog() {
        new SignInCancelDialog(this, new OnDialogClickListener() {
            @Override public void onPositiveClick() {
                finish();
            }

            @Override public void onNegativeClick() {
            }
        }).show();
    }

    @Override public void showLimitJoinDialog() {
        new LimitJoinDialog(this).show();
    }

    @Override public void showSignCompletedDialog() {
        DialogManager.showSignInCompletedDialog(this, presenter::onSignInCompletedDialogCloseClick);
    }

    @Override public void showVerifyCompleteButton() {
        verifyCompleteButton.post(() -> verifyCompleteButton.setVisibility(View.VISIBLE));
    }

    @Override public void checkFullAgree() {
        fullAgreeCheckBox.setImageDrawable(img_check_on);
    }

    @Override public void uncheckFullAgree() {
        fullAgreeCheckBox.setImageDrawable(img_check_off);
    }

    @Override public void checkTermsOfUse() {
        termsOfUseCheckBox.setImageDrawable(img_check_on);
    }

    @Override public void uncheckTermsOfUse() {
        termsOfUseCheckBox.setImageDrawable(img_check_off);
    }

    @Override public void checkPrivacyPolicy() {
        privacyPolicyCheckBox.setImageDrawable(img_check_on);
    }

    @Override public void uncheckPrivacyPolicy() {
        privacyPolicyCheckBox.setImageDrawable(img_check_off);
    }

    @Override public void checkCommercialInfoCheck() {
        commercialInfoCheckBox.setImageDrawable(img_check_on);
    }

    @Override public void uncheckCommercialInfoCheck() {
        commercialInfoCheckBox.setImageDrawable(img_check_off);
    }

    @Override public void enableJoinButton() {
        joinTextView.setBackgroundColor(color_FF8140E5);
        joinTextView.setEnabled(true);
    }

    @Override public void disableJoinButton() {
        joinTextView.setBackgroundColor(color_FFF5F5F5);
        joinTextView.setEnabled(false);
    }

    @OnClick(R.id.txt_certify)
    void onVerifyClick() {
        WebViewActivity.start(this, URLKeys.URL_IDENTITY_VERIFICATION);
    }

    @OnClick(R.id.layout_full_agree)
    void onFullAgreeClick() {
        presenter.onFullAgreeClick();
    }

    @OnClick(R.id.layout_terms_of_use)
    void onTermsOfUseClick() {
        presenter.onTermsOfUseClick();
    }

    @OnClick(R.id.layout_privacy_policy)
    void onPrivacyPolicyClick() {
        presenter.onPrivacyPolicyClick();
    }

    @OnClick(R.id.layout_commercial_info)
    void onCommercialClick() {
        presenter.onCommercialClick();
    }

    @OnClick(R.id.txt_signin)
    void onJoinClick() {
        presenter.onJoinClick();
    }

    @OnClick(R.id.img_titlebar_back)
    void onBackClick() {
        presenter.onBackClick();
    }
}
