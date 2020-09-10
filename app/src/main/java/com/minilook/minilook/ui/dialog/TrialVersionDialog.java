package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class TrialVersionDialog extends BaseDialog {

    public TrialVersionDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_trial_version;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @OnClick(R.id.img_close)
    void onOkClick() {
        this.dismiss();
        onPositiveClickListener.onPositiveClick();
    }
}
