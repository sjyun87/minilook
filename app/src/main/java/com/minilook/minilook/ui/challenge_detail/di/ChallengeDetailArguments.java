package com.minilook.minilook.ui.challenge_detail.di;

import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.challenge_detail.ChallengeDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeDetailArguments {
    private final ChallengeDetailPresenter.View view;
    private final int challengeNo;
    private final BaseAdapterDataModel<String> imageAdapter;
}