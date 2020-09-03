package com.minilook.minilook.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;

import butterknife.OnClick;

public class SignInCompletedDialog extends BaseDialog {

    private OnCloseClickListener listener;

    public SignInCompletedDialog(@NonNull Context context) {
        super(context);
        this.listener = listener;
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_signin_completed;
    }

    @OnClick(R.id.img_close)
    void onClose() {
        this.dismiss();
        listener.onCloseClick();
    }
}
