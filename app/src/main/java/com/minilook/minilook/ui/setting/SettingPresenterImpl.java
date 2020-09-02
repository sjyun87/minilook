package com.minilook.minilook.ui.setting;

import com.minilook.minilook.App;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.setting.di.SettingArguments;

public class SettingPresenterImpl extends BasePresenterImpl implements SettingPresenter {

    private final View view;
    private final MemberRequest memberRequest;

    public SettingPresenterImpl(SettingArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        view.setupInfoSwitchButton();
        view.setupMarketingSwitchButton();
    }

    @Override public void onLogoutClick() {
        App.getInstance().clearUserId();
    }

    @Override public void onInfoNotifyChecked(boolean isChecked) {
        addDisposable(memberRequest.updateInfoNotify(isChecked)
            .compose(Transformer.applySchedulers())
            .subscribe());
    }

    @Override public void onMarketingChecked(boolean isChecked) {
        addDisposable(memberRequest.updateMarketing(isChecked)
            .compose(Transformer.applySchedulers())
            .subscribe());
    }
}