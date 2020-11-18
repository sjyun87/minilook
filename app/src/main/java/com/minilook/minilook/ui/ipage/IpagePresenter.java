package com.minilook.minilook.ui.ipage;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface IpagePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onLogin();

    void onLogout();

    void onCurtainClick();

    void onProfileClick();

    void onPointClick();

    void onCouponClick();

    void onScrapBookClick();

    void onShoppingBagClick();

    void onRecentClick();

    void onOrderMoreClick();

    void onOrderCompleteClick();

    void onPackingClick();

    void onDeliveryClick();

    void onDeliveryCompleteClick();

    void onQuestionClick();

    void onNoticeClick();

    void onFAQClick();

    interface View {

        void setupLogin();

        void setupNick(String nick);

        void setupPoint(int point);

        void setupCoupon(int coupon);

        void setupOrderComplete(int count);

        void setupPacking(int count);

        void setupDelivery(int count);

        void setupDeliveryComplete(int count);

        void showCurtain();

        void hideCurtain();

        void navigateToLogin();

        void navigateToProfile();

        void navigateToPoint();

        void navigateToCoupon();

        void navigateToScrapBook();

        void navigateToShoppingBag();

        void navigateToRecent();

        void navigateToOrderMore();

        void navigateToQuestion();

        void navigateToWebView(String url);
    }
}
