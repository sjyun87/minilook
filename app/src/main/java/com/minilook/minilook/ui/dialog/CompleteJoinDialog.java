package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.dialog.listener.OnButtonClickListener;

public class CompleteJoinDialog extends Dialog {

    private Context context;
    private OnButtonClickListener listener;

    public CompleteJoinDialog(@NonNull Context context, @NonNull OnButtonClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_complete_join);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_close)
    void onClose() {
        this.dismiss();
        listener.onNegativeClick();
    }
}
