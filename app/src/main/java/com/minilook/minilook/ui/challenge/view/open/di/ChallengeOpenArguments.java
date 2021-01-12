package com.minilook.minilook.ui.challenge.view.open.di;

import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.challenge.view.open.ChallengeOpenPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeOpenArguments {
    private final ChallengeOpenPresenter.View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
}