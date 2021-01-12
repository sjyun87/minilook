package com.minilook.minilook.ui.challenge_detail;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import timber.log.Timber;

public class ChallengeDetailPresenterImpl extends BasePresenterImpl implements ChallengeDetailPresenter {

    private final View view;
    private final int challengeNo;
    private final ChallengeRequest challengeRequest;
    private final Gson gson;

    public ChallengeDetailPresenterImpl(ChallengeDetailArguments args) {
        view = args.getView();
        challengeNo = args.getChallengeNo();
        challengeRequest = new ChallengeRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {

        getChallengeDetail();
    }

    @Override public void onDestroy() {
    }

    private void getChallengeDetail() {
        addDisposable(challengeRequest.getChallengeDetail(challengeNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ChallengeDataModel.class))
            .subscribe(this::onResChallengeDetail, Timber::e));
    }

    private void onResChallengeDetail(ChallengeDataModel data) {
    }
}