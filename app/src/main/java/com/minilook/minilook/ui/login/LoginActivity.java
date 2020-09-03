package com.minilook.minilook.ui.login;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.join.JoinActivity;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import com.minilook.minilook.ui.login.listener.OnSNSLoginListener;
import com.minilook.minilook.ui.login.naver.NaverLoginManager;

public class LoginActivity extends BaseActivity implements LoginPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private LoginPresenter presenter;
    private KakaoLoginManager kakaoLoginManager = new KakaoLoginManager(this);
    private NaverLoginManager naverLoginManager = new NaverLoginManager(this);

    @Override protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override protected void createPresenter() {
        presenter = new LoginPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LoginArguments provideArguments() {
        return LoginArguments.builder()
            .view(this)
            .kakaoLoginManager(kakaoLoginManager)
            .naverLoginManager(naverLoginManager)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void setupKakaoLoginManager() {
        kakaoLoginManager.setListener(snsLoginListener);
    }

    @Override public void setupNaverLoginManager() {
        naverLoginManager.setListener(snsLoginListener);
    }

    private OnSNSLoginListener snsLoginListener = new OnSNSLoginListener() {
        @Override public void onSNSLogin(String sns_id, String email, String type) {
            presenter.onSNSLogin(sns_id, email, type);
        }

        @Override public void onSNSLogout() {
        }

        @Override public void onSNSError(int errorCode, String message) {
            presenter.onSNSError(errorCode, message);
        }
    };

    @Override public void showNoEmailDialog() {
        DialogManager.showNoEmailDialog(this);
    }

    @Override public void navigateToJoin(UserDataModel userData) {
        JoinActivity.start(this, userData);
    }

    @OnClick(R.id.layout_naver_panel)
    void onNaverClick() {
        presenter.onNaverClick();
    }

    @OnClick(R.id.layout_kakao_panel)
    void onKakaoClick() {
        presenter.onKakaoClick();
    }

    @OnClick(R.id.img_titlebar_close)
    void onCloseClick() {
        presenter.onCloseClick();
    }
}
