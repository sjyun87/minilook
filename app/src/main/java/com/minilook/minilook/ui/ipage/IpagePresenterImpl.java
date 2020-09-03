package com.minilook.minilook.ui.ipage;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.ipage.IpageDataModel;
import com.minilook.minilook.data.network.ipage.IpageRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.ipage.di.IpageArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class IpagePresenterImpl extends BasePresenterImpl implements IpagePresenter {

    private final View view;
    private final IpageRequest ipageRequest;

    private Gson gson = new Gson();

    public IpagePresenterImpl(IpageArguments args) {
        view = args.getView();
        ipageRequest = new IpageRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        if (App.getInstance().isLogin()) {
            setupUser();
        } else {
            setupNonUser();
        }
    }

    @Override public void onLogin() {
        setupUser();
    }

    @Override public void onLogout() {
        setupNonUser();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventNickChanged) {
                String nick = ((RxBusEventNickChanged) o).getNick();
                view.setupNick(nick);
            }
        }, Timber::e));
    }

    private void setupUser() {
        view.hideCurtain();
        reqIpage();
    }

    private void setupNonUser() {
        view.showCurtain();
        view.setupLogin();
        view.setupPoint(0);
        view.setupCoupon(0);
        view.setupOrderComplete(0);
        view.setupPacking(0);
        view.setupDelivery(0);
        view.setupDeliveryComplete(0);
    }

    private void reqIpage() {
        addDisposable(ipageRequest.getIpage()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), IpageDataModel.class))
            .subscribe(this::resIpage, Timber::e));
    }

    private void resIpage(IpageDataModel data) {
        view.setupNick(data.getNick());
        view.setupPoint(data.getPoint());
        view.setupCoupon(data.getCoupon());
        view.setupOrderComplete(data.getOrder_complete());
        view.setupPacking(data.getPacking());
        view.setupDelivery(data.getDelivery());
        view.setupDeliveryComplete(data.getDelivery_complete());
    }

    @Override public void onCurtainClick() {
        view.navigateToLogin();
    }

    @Override public void onProfileClick() {
        view.navigateToProfile();
    }

    @Override public void onPointClick() {
        view.navigateToPoint();
    }

    @Override public void onCouponClick() {
        view.navigateToCoupon();
    }

    @Override public void onScrapBookClick() {
        view.navigateToScrapBook();
    }

    @Override public void onShoppingBagClick() {
        view.navigateToShoppingBag();
    }

    @Override public void onRecentClick() {
        view.navigateToRecent();
    }

    @Override public void onOrderMoreClick() {
        view.navigateToOrderMore();
    }

    @Override public void onOrderCompleteClick() {
        view.navigateToOrderMore();
    }

    @Override public void onPackingClick() {
        view.navigateToOrderMore();
    }

    @Override public void onDeliveryClick() {
        view.navigateToOrderMore();
    }

    @Override public void onDeliveryCompleteClick() {
        view.navigateToOrderMore();
    }

    @Override public void onQuestionClick() {
        view.navigateToQuestion();
    }

    @Override public void onNoticeClick() {
        view.navigateToWebView(URLKeys.URL_NOTICE);
    }

    @Override public void onFAQClick() {
        view.navigateToWebView(URLKeys.URL_FAQ);
    }

    @AllArgsConstructor @Getter public final static class RxBusEventNickChanged {
        String nick;
    }
}