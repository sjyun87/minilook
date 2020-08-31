package com.minilook.minilook.ui.base.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.minilook.minilook.ui.base.widget.ToastView;
import lombok.Builder;
import lombok.NonNull;

public final class ToastManager {

    public static void showToast(@NonNull Context context, @NonNull String message) {
        showToast(context, message, null, null);
    }

    public static void showToast(@NonNull Context context, @NonNull String message, String buttonName, ToastView.OnButtonClickListener listener) {
        Toast toast = new Toast(context);
        ToastView toastView = ToastView.builder()
            .context(context)
            .message(message)
            .buttonName(buttonName)
            .listener(listener)
            .build();
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }
}
