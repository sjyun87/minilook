package com.minilook.minilook.ui.leave;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.login.LoginRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.leave.di.LeaveArguments;
import timber.log.Timber;

public class LeavePresenterImpl extends BasePresenterImpl implements LeavePresenter {

    private final View view;
    private final LoginRequest loginRequest;

    public LeavePresenterImpl(LeaveArguments args) {
        view = args.getView();
        loginRequest = new LoginRequest();
    }

    @Override public void onCreate() {
        view.setupPoint(2000);
        view.setupCoupon(3);
        view.setupChainSNS(App.getInstance().getSnsType());
    }

    @Override public void onLeaveClick() {
        reqLeave();
    }

    private void reqLeave() {
        addDisposable(loginRequest.leave()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe(this::resLeave, Timber::e));
    }

    private void resLeave(BaseDataModel data) {
        App.getInstance().setupLogout();
        view.navigateToMain();
        view.finish();

        // TODO 토스트 메시지
    }
}