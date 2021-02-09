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
import com.minilook.minilook.ui.order_cancel.OrderCancelPresenterImpl;
import com.minilook.minilook.ui.review_write.ReviewWritePresenterImpl;
import com.minilook.minilook.util.TrackingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class IpagePresenterImpl extends BasePresenterImpl implements IpagePresenter {

    private final View view;
    private final IpageRequest ipageRequest;
    private final Gson gson;

    public IpagePresenterImpl(IpageArguments args) {
        view = args.getView();
        ipageRequest = new IpageRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();

        handlePage();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("아이페이지", IpageFragment.class.getSimpleName());
    }

    @Override public void onLogin() {
        setupUser();
    }

    @Override public void onLogout() {
        setupNonUser();
    }

    private void handlePage() {
        if (App.getInstance().isLogin()) {
            setupUser();
        } else {
            setupNonUser();
        }
    }

    private void setupUser() {
        view.hideCurtain();
        getIpage();
    }

    private void setupNonUser() {
        view.showCurtain();
        view.setLoginButton();
        view.setPoint(0);
        view.setCoupon(0);
        view.setOrderComplete(0);
        view.setPacking(0);
        view.setDelivery(0);
        view.setDeliveryComplete(0);
    }

    private void getIpage() {
        addDisposable(ipageRequest.getIpage()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), IpageDataModel.class))
            .subscribe(this::onResIpage, Timber::e));
    }

    private void onResIpage(IpageDataModel data) {
        view.setNick(data.getNick());
        view.setPoint(data.getPoint());
        view.setCoupon(data.getCoupon());
        view.setOrderComplete(data.getOrder_complete());
        view.setPacking(data.getPacking());
        view.setDelivery(data.getDelivery());
        view.setDeliveryComplete(data.getDelivery_complete());
        if (data.getShoppingbagCount() > 0) view.setShoppingBagCount(data.getShoppingbagCount());
        if (data.getReviewCount() > 0) view.setReviewCount(data.getReviewCount());
        if (data.getQuestionCount() > 0) view.setQuestionCount(data.getQuestionCount());
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

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventNickChanged) {
                String nick = ((RxBusEventNickChanged) o).getNick();
                view.setNick(nick);
            } else if (o instanceof RxBusEventDataChanged) {
                setupUser();
            } else if (o instanceof ReviewWritePresenterImpl.RxEventReviewWrite) {
                getIpage();
            } else if (o instanceof OrderCancelPresenterImpl.RxBusEventOrderCancelCompleted) {
                getIpage();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventNickChanged {
        String nick;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventDataChanged {
    }
}