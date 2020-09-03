package com.minilook.minilook.ui.dialog.manager;

import android.app.Activity;

import com.minilook.minilook.ui.dialog.SignInCompletedDialog;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;

import lombok.Builder;

public final class DialogManager {

    @Builder
    public static void showSignInCompletedDialog(Activity activity, OnCloseClickListener listener) {
        SignInCompletedDialog.builder()
            .context(activity)
            .onCloseClickListener(listener)
            .build()
            .show();
    }
}
