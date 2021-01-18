package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class ChallengeAlreadyEnterDialog extends BaseDialog {

    public ChallengeAlreadyEnterDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_challenge_already_enter;
    }

    @OnClick(R.id.img_close)
    void onClose() {
        this.dismiss();
    }
}
