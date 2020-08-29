package com.minilook.minilook.ui.base.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.minilook.minilook.ui.base.widget.ToastView;

public final class ToastManager {

    public static void showToast(Context context, String message) {
        Toast toast = new Toast(context);
        ToastView toastView = ToastView.builder()
            .context(context)
            .message(message)
            .build();
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }
}
