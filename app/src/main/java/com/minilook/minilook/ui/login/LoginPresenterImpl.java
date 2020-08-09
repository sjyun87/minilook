package com.minilook.minilook.ui.login;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;

public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter {

    private final View view;
    private final KakaoLoginManager kakaoLoginManager;

    public LoginPresenterImpl(LoginArguments args) {
        view = args.getView();
        kakaoLoginManager = args.getKakaoLoginManager();
    }

    @Override public void onCreate() {

    }

    @Override public void onNaverClick() {
    }

    @Override public void onKakaoClick() {
        //kakaoLoginManager.login();
        //view.showNoEmailDialog();
    }
}