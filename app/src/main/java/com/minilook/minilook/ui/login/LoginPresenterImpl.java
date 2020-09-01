package com.minilook.minilook.ui.login;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.SchedulersFacade;
import com.minilook.minilook.data.type.NetworkType;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.join.JoinPresenterImpl;
import com.minilook.minilook.ui.login.di.LoginArguments;
import com.minilook.minilook.ui.login.kakao.KakaoLoginManager;
import com.minilook.minilook.ui.login.naver.NaverLoginManager;
import timber.log.Timber;

public class LoginPresenterImpl extends BasePresenterImpl implements LoginPresenter {

    private final View view;
    private final KakaoLoginManager kakaoLoginManager;
    private final NaverLoginManager naverLoginManager;
    private final MemberRequest memberRequest;

    private Gson gson = new Gson();
    private UserDataModel userData;

    public LoginPresenterImpl(LoginArguments args) {
        view = args.getView();
        kakaoLoginManager = args.getKakaoLoginManager();
        naverLoginManager = args.getNaverLoginManager();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupKakaoLoginManager();
        view.setupNaverLoginManager();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof JoinPresenterImpl.RxEventJoinComplete) {
                view.finish();
            }
        }, Timber::e));
    }

    @Override public void onNaverClick() {
        naverLoginManager.login();
    }

    @Override public void onKakaoClick() {
        kakaoLoginManager.login();
    }

    @Override public void onLoginSuccess(String sns_id, String email, String type) {
        userData = new UserDataModel();
        userData.setSns_id(sns_id);
        userData.setEmail(email);
        userData.setType(type);

        reqCheckUser();
    }

    @Override public void onLoginError(int errorCode, String message) {
        Timber.e(errorCode + " = " + message);
        if (errorCode == KakaoLoginManager.ERROR_NO_EMAIL) {
            view.showNoEmailDialog();
        }
    }

    private void reqCheckUser() {
        addDisposable(memberRequest.checkUser(userData)
            .subscribeOn(SchedulersFacade.io())
            .observeOn(SchedulersFacade.io())
            .subscribe(this::resCheckUser, Timber::e));
    }

    private void resCheckUser(BaseDataModel data) {
        if (data.getCode().equals(NetworkType.OK)) {
            userData = gson.fromJson(data.getData(), UserDataModel.class);
            App.getInstance().setLogin(true);
            App.getInstance().setUserId(userData.getUser_id());
            App.getInstance().setSnsId(userData.getSns_id());
            App.getInstance().setSnsType(userData.getType());
            view.finish();
        } else if (data.getCode().equals(NetworkType.NO_DATA)) {
            view.navigateToJoin(userData);
        }
    }
}