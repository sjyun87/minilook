package com.minilook.minilook.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.base.widget.CustomToast;
import com.minilook.minilook.ui.leave.LeaveActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.setting.di.SettingArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;
import com.suke.widget.SwitchButton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class SettingActivity extends BaseActivity implements SettingPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_order_info_panel) ConstraintLayout orderInfoPanel;
    @BindView(R.id.sb_order_info) SwitchButton orderInfoSwitchButton;
    @BindView(R.id.sb_marketing_info) SwitchButton marketingInfoSwitchButton;
    @BindView(R.id.txt_version) TextView versionTextView;
    @BindView(R.id.txt_login) TextView loginTextView;
    @BindView(R.id.txt_logout) TextView logoutTextView;
    @BindView(R.id.txt_leave) TextView leaveTextView;

    @BindString(R.string.base_toast_marketing_info_agree) String str_toast_marketing_agree;
    @BindString(R.string.base_toast_marketing_info_disagree) String str_toast_marketing_disagree;
    @BindString(R.string.base_toast_order_info_agree) String str_toast_order_agree;
    @BindString(R.string.base_toast_order_info_disagree) String str_toast_order_disagree;
    @BindString(R.string.support_email) String support_email;

    private SettingPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override protected void createPresenter() {
        presenter = new SettingPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SettingArguments provideArguments() {
        return SettingArguments.builder()
            .view(this)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void onLogout() {
        presenter.onLogout();
    }

    @Override public void setupInfoSwitchButton() {
        orderInfoSwitchButton.setOnCheckedChangeListener(
            (view, isChecked) -> {
                presenter.onOrderInfoChecked(isChecked);
                if (isChecked) {
                    //CustomToast.make(this, str_toast_order_agree).show();
                    Toast.makeText(this, str_toast_order_agree, Toast.LENGTH_SHORT).show();
                } else {
                    //CustomToast.make(this, str_toast_order_disagree).show();
                    Toast.makeText(this, str_toast_order_disagree, Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override public void setupMarketingSwitchButton() {
        marketingInfoSwitchButton.setOnCheckedChangeListener(
            (view, isChecked) -> {
                presenter.onMarketingInfoChecked(isChecked);
                if (isChecked) {
                    //CustomToast.make(this, str_toast_marketing_agree).show();
                    Toast.makeText(this, str_toast_marketing_agree, Toast.LENGTH_SHORT).show();
                } else {
                    //CustomToast.make(this, str_toast_marketing_disagree).show();
                    Toast.makeText(this, str_toast_marketing_disagree, Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override public void showOrderInfoPanel() {
        orderInfoPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideOrderInfoPanel() {
        orderInfoPanel.setVisibility(View.GONE);
    }

    @Override public void setupCurrentVersion() {
        versionTextView.setText(BuildConfig.VERSION_NAME);
    }

    @Override public void showLoginButton() {
        loginTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideLoginButton() {
        loginTextView.setVisibility(View.GONE);
    }

    @Override public void showLogoutButton() {
        logoutTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideLogoutButton() {
        logoutTextView.setVisibility(View.GONE);
    }

    @Override public void showLeaveButton() {
        leaveTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideLeaveButton() {
        leaveTextView.setVisibility(View.GONE);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void navigateToLeave() {
        LeaveActivity.start(this);
    }

    @Override public void navigateToWebView(String url) {
        WebViewActivity.start(this, url);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this, BottomBar.POSITION_LOOKBOOK);
    }

    @Override public void navigateToSendEmail() {
        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse("mailto:"));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { support_email });
        Map<String, String> mailData = getMailData();
        intent.putExtra(Intent.EXTRA_SUBJECT, mailData.get("title"));
        intent.putExtra(Intent.EXTRA_TEXT, mailData.get("contents"));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setSelector(emailSelectorIntent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.txt_terms_of_use)
    void onTermsOfUseClick() {
        presenter.onTermsOfUseClick();
    }

    @OnClick(R.id.txt_privacy_policy)
    void onPrivacyPolicyClick() {
        presenter.onPrivacyPolicyClick();
    }

    @OnClick(R.id.txt_app_question)
    void onAppQuestionClick() {
        presenter.onAppQuestionClick();
    }

    @OnClick(R.id.txt_login)
    void onLoginClick() {
        presenter.onLoginClick();
    }

    @OnClick(R.id.txt_logout)
    void onLogoutClick() {
        presenter.onLogoutClick();
    }

    @OnClick(R.id.txt_leave)
    void onLeaveClick() {
        presenter.onLeaveClick();
    }

    private Map<String, String> getMailData() {
        Map<String, String> mailData = new HashMap<>();
        InputStream inputStream = getResources().openRawResource(R.raw.support_email);
        InputStreamReader stream = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(stream);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            int index = 0;
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    mailData.put("title", line);
                } else {
                    sb.append(line);
                    sb.append("\n");
                }
                index++;
            }
            mailData.put("contents",
                String.format(sb.toString(), Build.VERSION.SDK_INT, BuildConfig.VERSION_NAME, Build.DEVICE));
        } catch (IOException e) {
            Timber.e(e);
        }
        return mailData;
    }
}
