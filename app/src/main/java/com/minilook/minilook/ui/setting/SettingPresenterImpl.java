package com.minilook.minilook.ui.setting;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.login.LoginRequest;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.setting.di.SettingArguments;
import timber.log.Timber;

public class SettingPresenterImpl extends BasePresenterImpl implements SettingPresenter {

    private final View view;
    private final MemberRequest memberRequest;
    private final LoginRequest loginRequest;

    public SettingPresenterImpl(SettingArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
        loginRequest = new LoginRequest();
    }

    @Override public void onCreate() {
        view.setupInfoSwitchButton();
        view.setupMarketingSwitchButton();
        view.setupCurrentVersion();

        if (App.getInstance().isLogin()) {
            setupUser();
        } else {
            setupNonUser();
        }
    }

    @Override public void onLogin() {
        setupUser();
    }

    @Override public void onLogout() {
        setupNonUser();
    }

    @Override public void onLoginClick() {
        view.navigateToLogin();
    }

    @Override public void onLogoutClick() {
        reqLogout();
    }

    @Override public void onLeaveClick() {
        view.navigateToLeave();
    }

    @Override public void onOrderInfoChecked(boolean isChecked) {
        addDisposable(memberRequest.updateOrderInfo(isChecked)
            .subscribe());
    }

    @Override public void onMarketingInfoChecked(boolean isChecked) {
        addDisposable(memberRequest.updateMarketingInfo(isChecked)
            .subscribe());
    }

    @Override public void onTermsOfUseClick() {
        view.navigateToWebView(URLKeys.URL_TERMS_OF_USE);
    }

    @Override public void onPrivacyPolicyClick() {
        view.navigateToWebView(URLKeys.URL_PRIVACY_POLICY);
    }

    @Override public void onAppQuestionClick() {
        view.navigateToSendEmail();
    }

    private void setupUser() {
        view.showOrderInfoPanel();
        view.hideLoginButton();
        view.showLogoutButton();
        view.showLeaveButton();
    }

    private void setupNonUser() {
        view.hideOrderInfoPanel();
        view.showLoginButton();
        view.hideLogoutButton();
        view.hideLeaveButton();
    }

    private void reqLogout() {
        addDisposable(loginRequest.logout()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe(this::resLogout, Timber::e));
    }

    private void resLogout(BaseDataModel dataModel) {
        App.getInstance().setupLogout();
        view.navigateToMain();
    }
}