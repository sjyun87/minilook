package com.minilook.minilook.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.minilook.minilook.ui.dialog.listener.OnCancelClickListener;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnOkClickListener;

import butterknife.ButterKnife;
import lombok.Builder;

public abstract class BaseDialog extends Dialog {

    private OnOkClickListener onOkClickListener;
    private OnCancelClickListener onCancelClickListener;
    private OnCloseClickListener onCloseClickListener;

    @Builder
    public BaseDialog(@NonNull Context context,
        OnOkClickListener onOkClickListener,
        OnCancelClickListener onCancelClickListener,
        OnCloseClickListener onCloseClickListener) {
        super(context);

        this.onOkClickListener = onOkClickListener;
        this.onCancelClickListener = onCancelClickListener;
        this.onCloseClickListener = onCloseClickListener;
    }

    public BaseDialog(@NonNull Context context) {
        super(context);

        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutID();
}
