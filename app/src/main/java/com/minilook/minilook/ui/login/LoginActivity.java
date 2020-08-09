package com.minilook.minilook.ui.login;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.dialog.NoEmailDialog;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;

public class LoginActivity extends BaseActivity implements LoginPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private LoginPresenter presenter;
    private NoEmailDialog dialog;

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
            .kakaoLoginManager(new KakaoLoginManager(this))
            .build();
    }

    @OnClick(R.id.layout_naver_panel)
    void onNaverClick() {
        presenter.onNaverClick();
    }

    @OnClick(R.id.layout_kakao_panel)
    void onKakaoClick() {
        presenter.onKakaoClick();
    }

    @Override public void showNoEmailDialog() {
        if (dialog == null) dialog = new NoEmailDialog(this);
        dialog.show();
    }
}
