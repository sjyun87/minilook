package com.minilook.minilook.ui.join;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.common.CommonURL;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.type.LoginType;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.CompleteJoinDialog;
import com.minilook.minilook.ui.dialog.LimitJoinDialog;
import com.minilook.minilook.ui.dialog.ResetJoinDialog;
import com.minilook.minilook.ui.dialog.listener.OnButtonClickListener;
import com.minilook.minilook.ui.join.di.JoinArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;

public class JoinActivity extends BaseActivity implements JoinPresenter.View {

    public static void start(Context context, UserDataModel userData) {
        Intent intent = new Intent(context, JoinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("userData", userData);
        context.startActivity(intent);
    }

    @BindView(R.id.img_sns) ImageView snsImageView;
    @BindView(R.id.txt_chain_sns) TextView snsTextView;
    @BindView(R.id.txt_email) TextView emailTextView;
    @BindView(R.id.txt_verify) TextView verifyButton;
    @BindView(R.id.layout_verify_complete) LinearLayout verifyCompleteButton;
    @BindView(R.id.img_full_agree_check) ImageView fullAgreeCheckBox;
    @BindView(R.id.img_terms_of_use_check) ImageView termsOfUseCheckBox;
    @BindView(R.id.img_privacy_policy_check) ImageView privacyPolicyCheckBox;
    @BindView(R.id.img_commercial_info_check) ImageView commercialInfoCheckBox;
    @BindView(R.id.txt_join) TextView joinTextView;

    @BindDrawable(R.drawable.ic_kakao) Drawable img_kakao;
    @BindDrawable(R.drawable.ic_naver) Drawable img_naver;
    @BindDrawable(R.drawable.ic_checkbox2_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox2_on) Drawable img_check_on;

    @BindString(R.string.join_chain_kakao) String str_chain_kakao;
    @BindString(R.string.join_chain_naver) String str_chain_naver;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;

    private JoinPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_join;
    }

    @Override protected void createPresenter() {
        presenter = new JoinPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private JoinArguments provideArguments() {
        return JoinArguments.builder()
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
        new ResetJoinDialog(this, new OnButtonClickListener() {
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

    @Override public void showCompleteJoinDialog() {
        new CompleteJoinDialog(this, new OnButtonClickListener() {
            @Override public void onPositiveClick() {
            }

            @Override public void onNegativeClick() {
                presenter.onCompleteJoinDialogClose();
            }
        }).show();
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

    @OnClick(R.id.txt_verify)
    void onVerifyClick() {
        WebViewActivity.start(this, CommonURL.URL_IDENTITY_VERIFICATION);
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

    @OnClick(R.id.txt_join)
    void onJoinClick() {
        presenter.onJoinClick();
    }

    @OnClick(R.id.img_titlebar_back)
    void onBackClick() {
        presenter.onBackClick();
    }
}
