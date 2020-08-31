package com.minilook.minilook.ui.login;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import com.minilook.minilook.ui.login.naver.NaverLoginManager;
import timber.log.Timber;

public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter {

    private final View view;
    private final KakaoLoginManager kakaoLoginManager;
    private final NaverLoginManager naverLoginManager;
    private final MemberRequest memberRequest;

    public LoginPresenterImpl(LoginArguments args) {
        view = args.getView();
        kakaoLoginManager = args.getKakaoLoginManager();
        naverLoginManager = args.getNaverLoginManager();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        view.setupKakaoLoginManager();
        view.setupNaverLoginManager();
    }

    @Override public void onNaverClick() {
        naverLoginManager.login();
    }

    @Override public void onKakaoClick() {
        kakaoLoginManager.login();
    }

    @Override public void onLoginSuccess(String email, String type) {
        addDisposable(memberRequest.checkUser(getUserData(email, type))
            .subscribeOn(SchedulersFacade.io())
            .observeOn(SchedulersFacade.io())
            .subscribe(this::resCheckResult, Timber::e));
    }

    @Override public void onLoginError(int errorCode, String message) {
        Timber.e(errorCode + " = " + message);
        if (errorCode == KakaoLoginManager.ERROR_NO_EMAIL) {
            view.showNoEmailDialog();
        }
    }

    private UserDataModel getUserData(String email, String type) {
        UserDataModel model = new UserDataModel();
        model.setEmail(email);
        model.setType(type);
        return model;
    }

    private void resCheckResult(BaseDataModel data) {
        Timber.e(data.toString());
    }
}