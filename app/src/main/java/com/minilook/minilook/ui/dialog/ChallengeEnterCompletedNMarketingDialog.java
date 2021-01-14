package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class ChallengeEnterCompletedNMarketingDialog extends BaseDialog {

    public ChallengeEnterCompletedNMarketingDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_challenge_enter_completed_n_marketing;
    }

    @OnClick(R.id.txt_later)
    void onLaterClick() {
        this.dismiss();
        onNegativeClickListener.onNegativeClick();
    }

    @OnClick(R.id.txt_agree)
    void onAgreeClick() {
        this.dismiss();
        onPositiveClickListener.onPositiveClick();
    }
}
