package com.minilook.minilook.ui.join;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.join.di.JoinArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;
import com.pixplicity.easyprefs.library.Prefs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class JoinPresenterImpl extends BasePresenterImpl implements JoinPresenter {

    private final View view;
    private final UserDataModel userData;
    private final MemberRequest memberRequest;

    private Gson gson = new Gson();

    private boolean isVerifyComplete = false;
    private boolean isFullAgree = false;
    private boolean isTermOfUseCheck = false;
    private boolean isPrivacyPolicyCheck = false;
    private boolean isCommercialInfoCheck = false;

    public JoinPresenterImpl(JoinArguments args) {
        view = args.getView();
        userData = args.getUserData();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupChainSNS(userData.getType());
        view.setupEmail(userData.getEmail());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof WebViewActivity.RxEventIdentityVerificationComplete) {
                isVerifyComplete = true;
                String json = ((WebViewActivity.RxEventIdentityVerificationComplete) o).getJson();
                putUserData(json);
                view.showVerifyCompleteButton();
            }
        }, Timber::e));
    }

    private void putUserData(String json) {
        JsonObject data = gson.fromJson(json, JsonObject.class);
        String phone = data.get("phoneNumber").getAsString();
        String name = data.get("name").getAsString();
        String ci = data.get("ci").getAsString();
        String birth = data.get("birth_day").getAsString();

        userData.setPhone(phone);
        userData.setName(name);
        userData.setCi(ci);
        userData.setBirth(birth);
    }

    @Override public void onFullAgreeClick() {
        if (isFullAgree) {
            uncheckFullAgree();
            uncheckTermsOfUse();
            uncheckPrivacyPolicy();
            uncheckCommercialInfoCheck();
        } else {
            checkFullAgree();
            checkTermsOfUse();
            checkPrivacyPolicy();
            checkCommercialInfoCheck();
        }
        checkButtonEnable();
    }

    @Override public void onTermsOfUseClick() {
        if (isTermOfUseCheck) {
            uncheckTermsOfUse();
        } else {
            checkTermsOfUse();
        }
        setupCheckBox();
        checkButtonEnable();
    }

    @Override public void onPrivacyPolicyClick() {
        if (isPrivacyPolicyCheck) {
            uncheckPrivacyPolicy();
        } else {
            checkPrivacyPolicy();
        }
        setupCheckBox();
        checkButtonEnable();
    }

    @Override public void onCommercialClick() {
        if (isCommercialInfoCheck) {
            uncheckCommercialInfoCheck();
        } else {
            checkCommercialInfoCheck();
        }
        setupCheckBox();
        checkButtonEnable();
    }

    @Override public void onJoinClick() {
        reqJoin();
    }

    @Override public void onBackClick() {
        view.showResetJoinDialog();
    }

    @Override public void onCompleteJoinDialogClose() {
        RxBus.send(new RxEventJoinComplete());
        view.finish();
    }

    private void reqJoin() {
        addDisposable(memberRequest.join(userData)
            .map(data -> gson.fromJson(data.getData(), UserDataModel.class))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resJoin, Timber::e));
    }

    private void resJoin(UserDataModel data) {
        App.getInstance().setLogin(true);
        App.getInstance().setUserId(data.getUser_id());
        App.getInstance().setSnsId(data.getSns_id());
        App.getInstance().setSnsType(data.getType());
        Prefs.putInt(PrefsKey.KEY_LOGIN_VISIBLE_COUNT, 3);

        view.showCompleteJoinDialog();
    }

    public void checkFullAgree() {
        isFullAgree = true;
        view.checkFullAgree();
    }

    public void uncheckFullAgree() {
        isFullAgree = false;
        view.uncheckFullAgree();
    }

    public void checkTermsOfUse() {
        isTermOfUseCheck = true;
        view.checkTermsOfUse();
    }

    public void uncheckTermsOfUse() {
        isTermOfUseCheck = false;
        view.uncheckTermsOfUse();
    }

    public void checkPrivacyPolicy() {
        isPrivacyPolicyCheck = true;
        view.checkPrivacyPolicy();
    }

    public void uncheckPrivacyPolicy() {
        isPrivacyPolicyCheck = false;
        view.uncheckPrivacyPolicy();
    }

    public void checkCommercialInfoCheck() {
        isCommercialInfoCheck = true;
        view.checkCommercialInfoCheck();
        userData.setCommercialInfo(isCommercialInfoCheck);
    }

    public void uncheckCommercialInfoCheck() {
        isCommercialInfoCheck = false;
        view.uncheckCommercialInfoCheck();
        userData.setCommercialInfo(isCommercialInfoCheck);
    }

    private void setupCheckBox() {
        if (isTermOfUseCheck
            && isPrivacyPolicyCheck
            && isCommercialInfoCheck) {
            checkFullAgree();
        } else {
            uncheckFullAgree();
        }
    }

    private void checkButtonEnable() {
        if (isVerifyComplete
            && isTermOfUseCheck
            && isPrivacyPolicyCheck) {
            view.enableJoinButton();
        } else {
            view.disableJoinButton();
        }
    }

    @AllArgsConstructor @Getter public final static class RxEventJoinComplete {
    }
}