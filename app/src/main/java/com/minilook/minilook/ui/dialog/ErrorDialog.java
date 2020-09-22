package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.SpannableUtil;

public class ErrorDialog extends BaseDialog {

    @BindView(R.id.txt_contents) TextView contentsTextView;

    @BindString(R.string.dialog_error_contents) String str_error_contents;
    @BindString(R.string.dialog_error_contents_b) String str_error_contents_bold;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    public ErrorDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_error;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentsTextView.setText(getContentsText());
    }

    private SpannableString getContentsText() {
        return SpannableUtil.fontSpan(str_error_contents, str_error_contents_bold, font_bold);
    }

    @OnClick(R.id.txt_ok)
    void onOkClick() {
        this.dismiss();
    }
}
