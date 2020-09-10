package com.minilook.minilook.ui.dialog.manager;

import android.app.Activity;
import com.minilook.minilook.ui.dialog.DefaultShippingDialog;
import com.minilook.minilook.ui.dialog.JoinCancelDialog;
import com.minilook.minilook.ui.dialog.JoinCompletedDialog;
import com.minilook.minilook.ui.dialog.JoinLimitedDialog;
import com.minilook.minilook.ui.dialog.LeaveDialog;
import com.minilook.minilook.ui.dialog.MarketingInfoDialog;
import com.minilook.minilook.ui.dialog.NoEmailDialog;
import com.minilook.minilook.ui.dialog.TrialVersionDialog;
import com.minilook.minilook.ui.dialog.UpdateDialog;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnNegativeClickListener;
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
        MarketingInfoDialog dialog = new MarketingInfoDialog(activity);
        dialog.setOnPositiveClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showLeaveDialog(Activity activity, OnPositiveClickListener listener) {
        LeaveDialog dialog = new LeaveDialog(activity);
        dialog.setOnPositiveClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showDefaultShippingDialog(Activity activity, OnPositiveClickListener listener) {
        DefaultShippingDialog dialog = new DefaultShippingDialog(activity);
        dialog.setOnPositiveClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showUpdateDialog(Activity activity, OnPositiveClickListener onPositiveListener,
        OnNegativeClickListener onNegativeClickListener) {
        UpdateDialog dialog = new UpdateDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveListener);
        dialog.setOnNegativeClickListener(onNegativeClickListener);
        dialog.show();
    }

    @Builder
    public static void showTrialVersionDialog(Activity activity, OnPositiveClickListener onPositiveClickListener) {
        TrialVersionDialog dialog = new TrialVersionDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveClickListener);
        dialog.show();
    }
}
