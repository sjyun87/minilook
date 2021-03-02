package com.minilook.minilook.ui.coupon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.CouponDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.network.ipage.IpageRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.coupon.di.CouponArguments;
import com.minilook.minilook.ui.dialog.RegistCouponDialog;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class CouponPresenterImpl extends BasePresenterImpl implements CouponPresenter {

    private final View view;
    private final BaseAdapterDataModel<CouponDataModel> adapter;
    private final IpageRequest ipageRequest;
    private final CommonRequest commonRequest;

    private Gson gson = new Gson();

    public CouponPresenterImpl(CouponArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        ipageRequest = new IpageRequest();
        commonRequest = new CommonRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqCoupons();
    }

    @Override public void onCouponInfoClick() {
        view.navigateToWebView(URLKeys.URL_COUPON);
    }

    @Override public void onRegistCouponCodeClick() {
        view.showRegistCouponDialog();
    }

    @Override public void onRegistQRCodeClick() {
        view.showQRCodeScanner();
    }

    @Override public void onQRScan(String couponCode) {
        addDisposable(commonRequest.registCoupon(couponCode)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.OVERLAP_DATA)) {
                    view.showDuplicateMessage();
                } else if (code.equals(HttpCode.BAD_REQUEST)) {
                    view.showInvalidMessage();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::onResRegistCoupon, Timber::e)
        );
    }

    private void onResRegistCoupon(BaseDataModel model) {
        view.showRegistCompleteMessage();
        reqCoupons();
    }

    private void reqCoupons() {
        addDisposable(ipageRequest.getCoupons()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.emptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<CouponDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<CouponDataModel>>() {
                }.getType()))
            .subscribe(this::resCoupons, Timber::e));
    }

    private void resCoupons(List<CouponDataModel> data) {
        adapter.set(data);
        view.refresh();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RegistCouponDialog.RxEventCouponRegistCompleted) {
                reqCoupons();
            }
        }, Timber::e));
    }
}