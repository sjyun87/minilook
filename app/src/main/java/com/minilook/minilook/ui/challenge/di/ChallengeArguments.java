package com.minilook.minilook.ui.challenge.di;

import com.minilook.minilook.ui.challenge.ChallengePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChallengeArguments {
    private final ChallengePresenter.View view;
}