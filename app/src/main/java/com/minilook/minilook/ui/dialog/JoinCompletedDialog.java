package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class JoinCompletedDialog extends BaseDialog {

    public JoinCompletedDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_join_completed;
    }

    @OnClick(R.id.img_close)
    void onClose() {
        this.dismiss();
        onCloseClickListener.onCloseClick();
    }
}
