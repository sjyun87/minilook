package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class JoinLimitedDialog extends BaseDialog {

    public JoinLimitedDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_limit_join;
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
    }
}
