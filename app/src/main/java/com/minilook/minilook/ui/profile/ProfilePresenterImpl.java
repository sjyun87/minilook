package com.minilook.minilook.ui.profile;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.profile.di.ProfileArguments;

public class ProfilePresenterImpl extends BasePresenterImpl implements ProfilePresenter {

    private final View view;

    public ProfilePresenterImpl(ProfileArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}