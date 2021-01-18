package com.minilook.minilook.ui.challenge.view.coming.di;

import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.challenge.view.coming.ChallengeComingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeComingArguments {
    private final ChallengeComingPresenter.View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
}