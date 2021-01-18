package com.minilook.minilook.ui.challenge;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;
import com.minilook.minilook.ui.challenge.view.close.ChallengeClosePresenterImpl;
import com.minilook.minilook.ui.challenge.view.coming.ChallengeComingPresenterImpl;
import com.minilook.minilook.ui.challenge.view.open.ChallengeOpenPresenterImpl;
import com.minilook.minilook.util.TrackingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengePresenterImpl extends BasePresenterImpl implements ChallengePresenter {

    private final View view;

    private boolean isRefreshOpenChallenge = false;
    private boolean isRefreshComingChallenge = false;
    private boolean isRefreshCloseChallenge = false;

    public ChallengePresenterImpl(ChallengeArguments args) {
        view = args.getView();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupSwipeRefresh();
        view.setupTabLayout();
        view.setupViewPager();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("챌린지페이지", ChallengeFragment.class.getSimpleName());
    }

    @Override public void onLogin() {
        view.setHeaderExpand();
    }

    @Override public void onLogout() {
        view.setHeaderExpand();
    }

    @Override public void onSwipeRefresh() {
        RxBus.send(new RxBusEventSwipeRefresh());
    }

    private void checkRefreshCompleted() {
        if (isRefreshOpenChallenge && isRefreshComingChallenge && isRefreshCloseChallenge) {
            view.setRefreshing(false);
            isRefreshOpenChallenge = false;
            isRefreshComingChallenge = false;
            isRefreshCloseChallenge = false;
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToChallengeDetail) {
                int challengeNo = ((RxEventNavigateToChallengeDetail) o).getChallengeNo();
                view.navigateToChallengeDetail(challengeNo);
            } else if (o instanceof ChallengeOpenPresenterImpl.RxBusEventSwipeRefreshCompleted) {
                isRefreshOpenChallenge = true;
                checkRefreshCompleted();
            } else if (o instanceof ChallengeComingPresenterImpl.RxBusEventSwipeRefreshCompleted) {
                isRefreshComingChallenge = true;
                checkRefreshCompleted();
            } else if (o instanceof ChallengeClosePresenterImpl.RxBusEventSwipeRefreshCompleted) {
                isRefreshCloseChallenge = true;
                checkRefreshCompleted();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToChallengeDetail {
        private final int challengeNo;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSwipeRefresh {
    }
}