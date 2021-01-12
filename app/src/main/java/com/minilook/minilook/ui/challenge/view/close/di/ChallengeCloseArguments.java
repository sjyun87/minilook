package com.minilook.minilook.ui.challenge.view.close.di;

import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.challenge.view.close.ChallengeClosePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeCloseArguments {
    private final ChallengeClosePresenter.View view;
    private final BaseAdapterDataModel<ChallengeDataModel> adapter;
}