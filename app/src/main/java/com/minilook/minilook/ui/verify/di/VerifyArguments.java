package com.minilook.minilook.ui.verify.di;

import com.minilook.minilook.ui.verify.VerifyPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class VerifyArguments {
    private final VerifyPresenter.View view;
}