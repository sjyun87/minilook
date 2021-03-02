package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.SpannableUtil;

public class QuestionSecretDialog extends BaseDialog {

    @BindView(R.id.txt_contents) TextView contentTextView;
    @BindView(R.id.txt_ok) TextView okTextView;

    @BindString(R.string.dialog_question_content) String str_content;
    @BindString(R.string.dialog_question_content_bold) String str_content_bold;
    @BindString(R.string.dialog_question_secret) String str_secret;
    @BindString(R.string.dialog_question_public) String str_public;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private boolean isSecret;

    public QuestionSecretDialog(@NonNull Context context, boolean isSecret) {
        super(context);
        this.isSecret = isSecret;
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_question_secret_edit;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        contentTextView.setText(SpannableUtil.fontSpan(str_content, str_content_bold, font_bold));
        okTextView.setText(isSecret ? str_public : str_secret);
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
