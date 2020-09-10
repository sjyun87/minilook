package com.minilook.minilook.ui.leave;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.PointNCouponModel;
import com.minilook.minilook.data.network.login.LoginRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.leave.di.LeaveArguments;
import timber.log.Timber;

public class LeavePresenterImpl extends BasePresenterImpl implements LeavePresenter {

    private final View view;
    private final LoginRequest loginRequest;

    private Gson gson = new Gson();

    public LeavePresenterImpl(LeaveArguments args) {
        view = args.getView();
        loginRequest = new LoginRequest();
    }

    @Override public void onCreate() {
        view.setupChainSNS(App.getInstance().getSnsType());
        view.setupCoupon(0);
        view.setupPoint(0);

        reqPointNCoupon();
    }

    @Override public void onLeaveClick() {
        view.showLeaveDialog();
    }

    @Override public void onLeaveDialogOkClick() {
        reqLeave();
    }

    private void reqPointNCoupon() {
        addDisposable(loginRequest.getPointNCoupon()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), PointNCouponModel.class))
            .subscribe(this::resPointNCoupon, Timber::e));
    }

    private void resPointNCoupon(PointNCouponModel data) {
        Timber.e(data.toString());
        view.setupCoupon(data.getCoupon());
        view.setupPoint(data.getPoint());
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