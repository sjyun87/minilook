package com.minilook.minilook.ui.challenge;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengePresenterImpl extends BasePresenterImpl implements ChallengePresenter {

    private final View view;
    private final Gson gson;

    public ChallengePresenterImpl(ChallengeArguments args) {
        view = args.getView();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupTabLayout();
        view.setupViewPager();
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
}