package com.minilook.minilook.ui.join;

import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.join.di.JoinArguments;
import timber.log.Timber;

public class JoinPresenterImpl extends BasePresenterImpl implements JoinPresenter {

    private final View view;
    private final UserDataModel userData;

    private boolean isVerifyComplete = false;
    private boolean isFullAgree = false;
    private boolean isTermOfUseCheck = false;
    private boolean isPrivacyPolicyCheck = false;
    private boolean isCommercialInfoCheck = false;

    public JoinPresenterImpl(JoinArguments args) {
        view = args.getView();
        userData = args.getUserData();
    }

    @Override public void onCreate() {
        view.setupChainSNS(userData.getType());
        view.setupEmail(userData.getEmail());
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
        Timber.e("onJoinClick");
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
    }

    public void uncheckCommercialInfoCheck() {
        isCommercialInfoCheck = false;
        view.uncheckCommercialInfoCheck();
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
}