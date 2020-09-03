package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class JoinCancelDialog extends BaseDialog {

    public JoinCancelDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_join_cancel;
    }

    @OnClick(R.id.txt_cancel)
    void onCancelClick() {
        this.dismiss();
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
        onPositiveClickListener.onPositiveClick();
    }
}
