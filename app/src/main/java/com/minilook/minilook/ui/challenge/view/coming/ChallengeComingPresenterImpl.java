package com.minilook.minilook.ui.challenge.view.coming;

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
import com.minilook.minilook.ui.challenge.ChallengePresenterImpl;
import com.minilook.minilook.ui.challenge.view.coming.di.ChallengeComingArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengeComingPresenterImpl extends BasePresenterImpl implements ChallengeComingPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
    private final ChallengeRequest challengeRequest;
    private final Gson gson;

    private AtomicInteger page = new AtomicInteger(0);
    private int totalPageSize;

    public ChallengeComingPresenterImpl(ChallengeComingArguments args) {
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
        addDisposable(challengeRequest.getComingChallenge(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                    RxBus.send(new RxBusEventSwipeRefreshCompleted());
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
        RxBus.send(new RxBusEventSwipeRefreshCompleted());
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
            if (o instanceof ChallengePresenterImpl.RxBusEventSwipeRefresh) {
                view.scrollToTop();
                getChallenges();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSwipeRefreshCompleted {
    }
}