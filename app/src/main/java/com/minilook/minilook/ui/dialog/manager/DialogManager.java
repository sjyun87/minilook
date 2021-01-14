package com.minilook.minilook.ui.dialog.manager;

import android.app.Activity;
import com.minilook.minilook.ui.dialog.BrandCallDialog;
import com.minilook.minilook.ui.dialog.ChallengeEnterCompletedDialog;
import com.minilook.minilook.ui.dialog.ChallengeEnterCompletedNMarketingDialog;
import com.minilook.minilook.ui.dialog.DefaultShippingDialog;
import com.minilook.minilook.ui.dialog.ErrorDialog;
import com.minilook.minilook.ui.dialog.JoinCancelDialog;
import com.minilook.minilook.ui.dialog.JoinCompletedDialog;
import com.minilook.minilook.ui.dialog.JoinLimitedDialog;
import com.minilook.minilook.ui.dialog.LeaveDialog;
import com.minilook.minilook.ui.dialog.MarketingInfoDialog;
import com.minilook.minilook.ui.dialog.NoEmailDialog;
import com.minilook.minilook.ui.dialog.OutOfStockDialog;
import com.minilook.minilook.ui.dialog.PurchaseConfirmDialog;
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
    public static void showMarketingDialog(Activity activity, OnPositiveClickListener onPositiveClickListener,
        OnNegativeClickListener onNegativeClickListener) {
        MarketingInfoDialog dialog = new MarketingInfoDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveClickListener);
        dialog.setOnNegativeClickListener(onNegativeClickListener);
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
    public static void showUpdateDialog(Activity activity, OnPositiveClickListener onPositiveListener) {
        UpdateDialog dialog = new UpdateDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveListener);
        dialog.show();
    }

    @Builder
    public static void showOutOfStockDialog(Activity activity) {
        OutOfStockDialog dialog = new OutOfStockDialog(activity);
        dialog.show();
    }

    @Builder
    public static void showPurchaseConfirmDialog(Activity activity, OnPositiveClickListener onPositiveClickListener) {
        PurchaseConfirmDialog dialog = new PurchaseConfirmDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveClickListener);
        dialog.show();
    }

    @Builder
    public static void showErrorDialog(Activity activity) {
        ErrorDialog dialog = new ErrorDialog(activity);
        dialog.show();
    }

    @Builder
    public static void showBrandCallDialog(Activity activity, String brandName, String brandLogo, String csTime,
        OnPositiveClickListener onPositiveClickListener) {
        BrandCallDialog dialog = new BrandCallDialog(activity);
        dialog.setBrandName(brandName);
        dialog.setBrandLogo(brandLogo);
        dialog.setCsTimeInfo(csTime);
        dialog.setOnPositiveClickListener(onPositiveClickListener);
        dialog.show();
    }

    @Builder
    public static void showChallengeEnterCompletedDialog(Activity activity, OnCloseClickListener listener) {
        ChallengeEnterCompletedDialog dialog = new ChallengeEnterCompletedDialog(activity);
        dialog.setOnCloseClickListener(listener);
        dialog.show();
    }

    @Builder
    public static void showChallengeEnterCompletedNMarketingDialog(Activity activity,
        OnPositiveClickListener onPositiveClickListener,
        OnNegativeClickListener onNegativeClickListener) {
        ChallengeEnterCompletedNMarketingDialog dialog = new ChallengeEnterCompletedNMarketingDialog(activity);
        dialog.setOnPositiveClickListener(onPositiveClickListener);
        dialog.setOnNegativeClickListener(onNegativeClickListener);
        dialog.show();
    }
}
