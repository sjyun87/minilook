package com.minilook.minilook.ui.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;

public class QuestionDeleteDialog extends BaseDialog {

    public QuestionDeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_question_delete;
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
