package com.minilook.minilook.ui.challenge.view.open;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.view.open.di.ChallengeOpenArguments;
import com.minilook.minilook.ui.challenge_enter.ChallengeEnterPresenterImpl;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ChallengeOpenPresenterImpl extends BasePresenterImpl implements ChallengeOpenPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
    private final ChallengeRequest challengeRequest;
    private final Gson gson;

    private AtomicInteger page = new AtomicInteger(0);
    private int totalPageSize;

    public ChallengeOpenPresenterImpl(ChallengeOpenArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        challengeRequest = new ChallengeRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupRecyclerView();

        getChallenges();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLogin() {
        view.scrollToTop();
        getChallenges();
    }

    @Override public void onLogout() {
        view.scrollToTop();
        getChallenges();
    }

    @Override public void onLoadMore() {
        if (totalPageSize > page.get()) getMoreChallenges();
    }

    private void getChallenges() {
        page = new AtomicInteger(0);
        addDisposable(challengeRequest.getOpenChallenge(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<ChallengeDataModel>>) data -> {
                totalPageSize = data.getTotalPage();
                return gson.fromJson(data.getData(), new TypeToken<ArrayList<ChallengeDataModel>>() {
                }.getType());
            })
            .subscribe(this::onResChallenge, Timber::e));
    }

    private void onResChallenge(List<ChallengeDataModel> data) {
        adapter.set(data);
        view.refresh();
    }

    private void getMoreChallenges() {
        addDisposable(challengeRequest.getOpenChallenge(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<ChallengeDataModel>>) data -> {
                totalPageSize = data.getTotalPage();
                return gson.fromJson(data.getData(), new TypeToken<ArrayList<ChallengeDataModel>>() {
                }.getType());
            })
            .subscribe(this::onResMoreChallenge, Timber::e));
    }

    private void onResMoreChallenge(List<ChallengeDataModel> data) {
        int start = adapter.getSize();
        int rows = data.size();
        adapter.addAll(data);
        view.refresh(start, rows);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof ChallengeEnterPresenterImpl.RxEventEnterChallenge) {
                view.scrollToTop();
                getChallenges();
            }
        }, Timber::e));
    }
}