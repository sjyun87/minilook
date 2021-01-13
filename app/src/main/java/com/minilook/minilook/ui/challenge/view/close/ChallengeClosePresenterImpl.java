package com.minilook.minilook.ui.challenge.view.close;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.view.close.di.ChallengeCloseArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ChallengeClosePresenterImpl extends BasePresenterImpl implements ChallengeClosePresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
    private final ChallengeRequest challengeRequest;
    private final Gson gson;

    public ChallengeClosePresenterImpl(ChallengeCloseArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        challengeRequest = new ChallengeRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        view.setupRecyclerView();

        getChallenges();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    private void getChallenges() {
        addDisposable(challengeRequest.getCloseChallenge(ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<ChallengeDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ChallengeDataModel>>() {
                }.getType()))
            .subscribe(this::onResChallenge, Timber::e));
    }

    private void onResChallenge(List<ChallengeDataModel> data) {
        adapter.set(data);
        view.refresh();
    }
}