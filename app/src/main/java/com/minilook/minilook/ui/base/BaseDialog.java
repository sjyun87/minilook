package com.minilook.minilook.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.minilook.minilook.ui.dialog.listener.OnNegativeClickListener;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnPositiveClickListener;

import butterknife.ButterKnife;
import lombok.Builder;

public abstract class BaseDialog extends Dialog {

    protected OnPositiveClickListener onPositiveClickListener;
    protected OnNegativeClickListener onNegativeClickListener;
    protected OnCloseClickListener onCloseClickListener;

    @Builder
    public BaseDialog(@NonNull Context context,
        OnPositiveClickListener onPositiveClickListener,
        OnNegativeClickListener onNegativeClickListener,
        OnCloseClickListener onCloseClickListener) {
        super(context);

        this.onPositiveClickListener = onPositiveClickListener;
        this.onNegativeClickListener = onNegativeClickListener;
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
