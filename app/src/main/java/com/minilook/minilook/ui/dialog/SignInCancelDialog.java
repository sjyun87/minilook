package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.dialog.listener.OnOkClickListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInCancelDialog extends Dialog {

    private OnDialogClickListener listener;

    public SignInCancelDialog(@NonNull Context context, @NonNull OnOkClickListener listener) {
        super(context);
        this.listener = listener;

        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signin_cancel);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.txt_cancel)
    void onCancelClick() {
        this.dismiss();
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
        listener.onPositiveClick();
    }
}
