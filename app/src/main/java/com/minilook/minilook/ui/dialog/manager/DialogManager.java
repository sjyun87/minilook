package com.minilook.minilook.ui.dialog.manager;

import android.app.Activity;
import com.minilook.minilook.ui.dialog.JoinCancelDialog;
import com.minilook.minilook.ui.dialog.JoinCompletedDialog;
import com.minilook.minilook.ui.dialog.JoinLimitedDialog;
import com.minilook.minilook.ui.dialog.NoEmailDialog;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnPositiveClickListener;

public final class DialogManager {

    public static void showJoinCompletedDialog(Activity activity, OnCloseClickListener listener) {
        JoinCompletedDialog.builder()
            .context(activity)
            .onCloseClickListener(listener)
            .build()
            .show();
    }

    public static void showJoinCancelDialog(Activity activity, OnPositiveClickListener listener) {
        JoinCancelDialog.builder()
            .context(activity)
            .onPositiveClickListener(listener)
            .build()
            .show();
    }

    public static void showJoinLimitedDialog(Activity activity) {
        JoinLimitedDialog.builder()
            .context(activity)
            .build()
            .show();
    }

    public static void showNoEmailDialog(Activity activity) {
        NoEmailDialog.builder()
            .context(activity)
            .build()
            .show();
    }
}
