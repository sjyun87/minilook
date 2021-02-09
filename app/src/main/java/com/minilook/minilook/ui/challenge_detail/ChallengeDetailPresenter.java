package com.minilook.minilook.ui.challenge_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengeDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onLogin();

    void onLogout();

    void onEnterClick();

    void onShareClick();

    interface View {

        void setupClickAction();

        void showErrorDialog();

        void setupImageViewPager();

        void imageRefresh();

        void setupRelationProductViewPager();

        void relationProductRefresh();

        void startAutoSlide();

        void cancelAutoSlide();

        void setBrandName(String brandName);

        void setProductName(String productName);

        void showRemainTime(int day, int hour, int minute);

        void showEndTime();

        void setLabel(int status);

        void setTermDate(String start, String end);

        void setWinDate(String date);

        void setReviewDate(String date);

        void setWinnerCount(int count);

        void setDetailContent(String url);

        void setChallengeContent(String url);

        void showEnterButton();

        void showEnterCompletedButton();

        void showEndButton();

        void showComingButton();

        void enableEnterButton();

        void disableEnterButton();

        void showRelationProductPanel();

        void hideRelationProductPanel();

        void scrollToTop();

        void sendDynamicLink(String link);

        void navigateToChallengeEnter(int challengeNo);

        void navigateToLogin();

        void clear();

        void finish();
    }
}
