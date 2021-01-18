package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class ChallengeEndDialog extends BaseDialog {

    public ChallengeEndDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_challenge_end;
    }

    @OnClick(R.id.img_close)
    void onClose() {
        this.dismiss();
    }
}
