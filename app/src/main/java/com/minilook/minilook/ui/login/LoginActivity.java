package com.minilook.minilook.ui.login;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.type.LoginType;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.NoEmailDialog;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
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

    @Override public void setupKakaoLoginManager() {
        kakaoLoginManager.setOnKakaoLoginListener(new KakaoLoginManager.OnKakaoLoginListener() {
            @Override public void onKakaoLoginSuccess(String email) {
                presenter.onLoginSuccess(email, LoginType.KAKAO.getValue());
            }

            @Override public void onKakaoLogoutSuccess() {
            }

            @Override public void onKakaoError(int errorCode, String message) {
                presenter.onLoginError(errorCode, message);
            }
        });
    }

    @Override public void setupNaverLoginManager() {
        naverLoginManager.setOnNaverLoginListener(new NaverLoginManager.OnNaverLoginListener() {
            @Override public void onNaverLoginSuccess(String email) {
                presenter.onLoginSuccess(email, LoginType.NAVER.getValue());
            }

            @Override public void onNaverLogoutSuccess() {

            }

            @Override public void onNaverError(int errorCode, String message) {
                presenter.onLoginError(errorCode, message);
            }
        });
    }

    @Override public void showNoEmailDialog() {
        new NoEmailDialog(this).show();
    }

    @OnClick(R.id.layout_naver_panel)
    void onNaverClick() {
        presenter.onNaverClick();
    }

    @OnClick(R.id.layout_kakao_panel)
    void onKakaoClick() {
        presenter.onKakaoClick();
    }
}
