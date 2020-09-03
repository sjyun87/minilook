package com.minilook.minilook.ui.signin.di;

import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.ui.signin.SignInPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SignInArguments {
    private final SignInPresenter.View view;
    private final UserDataModel userData;
}