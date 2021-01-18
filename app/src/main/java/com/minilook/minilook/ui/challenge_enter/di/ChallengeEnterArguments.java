package com.minilook.minilook.ui.challenge_enter.di;

import com.minilook.minilook.ui.challenge_enter.ChallengeEnterPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeEnterArguments {
    private final ChallengeEnterPresenter.View view;
    private final int challengeNo;
}