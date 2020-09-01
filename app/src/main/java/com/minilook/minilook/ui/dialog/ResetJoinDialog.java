package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.dialog.listener.OnButtonClickListener;

public class ResetJoinDialog extends Dialog {

    private Context context;
    private OnButtonClickListener listener;

    public ResetJoinDialog(@NonNull Context context, @NonNull OnButtonClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reset_join);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.txt_cancel)
    void onCancelClick() {
        this.dismiss();
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        listener.onPositiveClick();
    }
}
