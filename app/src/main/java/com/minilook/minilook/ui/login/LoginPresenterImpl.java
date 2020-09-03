package com.minilook.minilook.ui.login;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.login.LoginRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import com.minilook.minilook.ui.login.naver.NaverLoginManager;
import timber.log.Timber;

public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter {

    private final View view;
    private final KakaoLoginManager kakaoLoginManager;
    private final NaverLoginManager naverLoginManager;
    private final LoginRequest loginRequest;

    private Gson gson = new Gson();
    private UserDataModel userData;

    public LoginPresenterImpl(LoginArguments args) {
        view = args.getView();
        kakaoLoginManager = args.getKakaoLoginManager();
        naverLoginManager = args.getNaverLoginManager();
        loginRequest = new LoginRequest();
    }

    @Override public void onCreate() {
        view.setupKakaoLoginManager();
        view.setupNaverLoginManager();
    }

    @Override public void onLogin() {
        view.finish();
    }

    @Override public void onNaverClick() {
        naverLoginManager.login();
    }

    @Override public void onKakaoClick() {
        kakaoLoginManager.login();
    }

    @Override public void onSNSLogin(String sns_id, String email, String type) {
        userData = new UserDataModel();
        userData.setSns_id(sns_id);
        userData.setEmail(email);
        userData.setType(type);

        reqLogin();
    }

    @Override public void onSNSError(int errorCode, String message) {
        Timber.e(errorCode + " = " + message);
        if (errorCode == KakaoLoginManager.ERROR_NO_EMAIL) {
            view.showNoEmailDialog();
        }
    }

    @Override public void onCloseClick() {
        view.finish();
    }

    private void reqLogin() {
        addDisposable(loginRequest.login(userData)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.navigateToJoin(userData);
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), UserDataModel.class))
            .subscribe(this::resLogin, Timber::e));
    }

    private void resLogin(UserDataModel data) {
        userData = data;
        App.getInstance().setupLogin(userData);
        view.navigateToMain();
        view.finish();
    }
}