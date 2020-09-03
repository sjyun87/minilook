package com.minilook.minilook.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import com.minilook.minilook.ui.dialog.listener.OnCloseClickListener;
import com.minilook.minilook.ui.dialog.listener.OnNegativeClickListener;
import com.minilook.minilook.ui.dialog.listener.OnPositiveClickListener;
import lombok.Setter;

public abstract class BaseDialog extends Dialog {
    @Setter protected OnPositiveClickListener onPositiveClickListener;
    @Setter protected OnNegativeClickListener onNegativeClickListener;
    @Setter protected OnCloseClickListener onCloseClickListener;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);

        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    protected abstract int getLayoutID();
}
