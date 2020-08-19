package com.minilook.minilook.ui.profile.di;

import com.minilook.minilook.ui.profile.ProfilePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProfileArguments {
    private final ProfilePresenter.View view;
}