package com.minilook.minilook.ui.base.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.minilook.minilook.R;

public final class DialogManager {

    public static void showRemoveAllDialog(Activity activity, OnButtonClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setMessage(activity.getResources().getString(R.string.search_recent_dialog_msg));
        builder.setPositiveButton(activity.getResources().getString(R.string.base_yes),
            (dialog, which) -> listener.onPositiveClick());
        builder.setNegativeButton(activity.getResources().getString(R.string.base_no),
            (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public interface OnButtonClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }
}
