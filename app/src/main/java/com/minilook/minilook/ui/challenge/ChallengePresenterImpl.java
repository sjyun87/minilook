package com.minilook.minilook.ui.challenge;

import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;
import com.minilook.minilook.util.TrackingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengePresenterImpl extends BasePresenterImpl implements ChallengePresenter {

    private final View view;

    public ChallengePresenterImpl(ChallengeArguments args) {
        view = args.getView();
    }

    @Override public void onCreateView() {
        toRxObservable();
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

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventNavigateToChallengeDetail) {
                int challengeNo = ((RxEventNavigateToChallengeDetail) o).getChallengeNo();
                view.navigateToChallengeDetail(challengeNo);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToChallengeDetail {
        private final int challengeNo;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSwipeRefresh {
    }
}