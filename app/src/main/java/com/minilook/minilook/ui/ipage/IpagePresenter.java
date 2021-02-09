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

    void onReviewClick();

    void onNoticeClick();

    void onFAQClick();

    interface View {

        void setupClickAction();

        void setLoginButton();

        void setNick(String nick);

        void setPoint(int point);

        void setCoupon(int coupon);

        void setOrderComplete(int count);

        void setPacking(int count);

        void setDelivery(int count);

        void setDeliveryComplete(int count);

        void setShoppingBagCount(int count);

        void setReviewCount(int count);

        void setQuestionCount(int count);

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

        void navigateToQuestionHistory();

        void navigateToReviewHistory();

        void navigateToWebView(String url);
    }
}
