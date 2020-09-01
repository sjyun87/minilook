package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;

public class LimitJoinDialog extends Dialog {

    private Context context;

    public LimitJoinDialog(@NonNull Context context) {
        super(context);
        this.context = context;

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_limit_join);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
    }
}
