package com.minilook.minilook.ui.splash.di;

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.messaging.FirebaseMessaging;
import com.minilook.minilook.ui.splash.SplashPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SplashArguments {
    private final SplashPresenter.View view;
    private final FirebaseDynamicLinks firebaseDynamicLinks;
    private final FirebaseMessaging firebaseMessaging;
}