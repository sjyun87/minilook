package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.SpannableUtil;

public class TrialVersionDialog extends BaseDialog {

    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.dialog_trial_version_desc) String str_desc;
    @BindString(R.string.dialog_trial_version_desc_b) String str_desc_bold;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

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

        setupDesc();
    }

    private void setupDesc() {
        SpannableString fontSpan = SpannableUtil.fontSpan(str_desc, str_desc_bold, font_bold);
        SpannableString colorSpan = SpannableUtil.foregroundColorSpan(fontSpan, str_desc_bold, color_FF8140E5);
        descTextView.setText(colorSpan);
    }

    @OnClick(R.id.img_close)
    void onOkClick() {
        this.dismiss();
    }
}
