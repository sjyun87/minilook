package com.minilook.minilook.ui.challenge;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;

public class ChallengePresenterImpl extends BasePresenterImpl implements ChallengePresenter {

    private final View view;
    private final Gson gson;

    public ChallengePresenterImpl(ChallengeArguments args) {
        view = args.getView();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
    }
}