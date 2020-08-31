package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;

public class NoEmailDialog extends Dialog {

    public NoEmailDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_no_email);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        this.dismiss();
    }
}
