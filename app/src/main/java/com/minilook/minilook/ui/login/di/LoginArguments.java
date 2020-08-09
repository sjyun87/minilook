package com.minilook.minilook.ui.login.di;

import com.minilook.minilook.ui.login.LoginPresenter;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LoginArguments {
    private final LoginPresenter.View view;
    private final KakaoLoginManager kakaoLoginManager;
}