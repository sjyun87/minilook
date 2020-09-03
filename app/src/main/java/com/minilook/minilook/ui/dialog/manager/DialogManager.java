package com.minilook.minilook.ui.dialog.manager;

import android.app.Activity;
import com.minilook.minilook.ui.dialog.JoinCancelDialog;
import com.minilook.minilook.ui.dialog.JoinCompletedDialog;
import com.minilook.minilook.ui.dialog.JoinLimitedDialog;
import com.minilook.minilook.ui.dialog.MarketingDialog;
import com.minilook.minilook.ui.dialog.NoEmailDialog;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnPositiveClickListener;
import lombok.Builder;

public final class DialogManager {

    @Builder
    public static void showJoinCompletedDialog(Activity activity, OnCloseClickListener listener) {
        JoinCompletedDialog dialog = new JoinCompletedDialog(activity);
        dialog.setOnCloseClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showJoinCancelDialog(Activity activity, OnPositiveClickListener listener) {
        JoinCancelDialog dialog = new JoinCancelDialog(activity);
        dialog.setOnPositiveClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showJoinLimitedDialog(Activity activity) {
        JoinLimitedDialog dialog = new JoinLimitedDialog(activity);
        dialog.show();
    }

    @Builder
    public static void showNoEmailDialog(Activity activity) {
        NoEmailDialog dialog = new NoEmailDialog(activity);
        dialog.show();
    }

    @Builder
    public static void showMarketingDialog(Activity activity, OnPositiveClickListener listener) {
        MarketingDialog dialog = new MarketingDialog(activity);
        dialog.setOnPositiveClickListener(listener);
        dialog.show();
    }
}