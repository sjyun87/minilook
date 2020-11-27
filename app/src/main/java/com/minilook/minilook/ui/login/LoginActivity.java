package com.minilook.minilook.ui.login;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.join.JoinActivity;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import com.minilook.minilook.ui.login.listener.OnSNSLoginListener;
import com.minilook.minilook.ui.login.naver.NaverLoginManager;
import com.minilook.minilook.ui.main.MainActivity;

public class LoginActivity extends _BaseActivity implements LoginPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        @Override public void onSNSLogin(String snsId, String email, String type) {
            presenter.onSNSLogin(snsId, email, type);
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

    @Override public void navigateToJoin(MemberDataModel userData) {
        JoinActivity.start(this, userData);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this, BottomBar.POSITION_LOOKBOOK);
    }

    @OnClick(R.id.layout_naver_panel)
    void onNaverClick() {
        presenter.onNaverClick();
    }

    @OnClick(R.id.layout_kakao_panel)
    void onKakaoClick() {
        presenter.onKakaoClick();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        presenter.onCloseClick();
    }
}
