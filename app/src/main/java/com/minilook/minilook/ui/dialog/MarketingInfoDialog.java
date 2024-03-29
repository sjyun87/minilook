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

public class MarketingInfoDialog extends BaseDialog {

    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.dialog_marketing_info_desc) String str_desc;
    @BindString(R.string.dialog_marketing_info_desc_b) String str_desc_bold;

    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    public MarketingInfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_marketing;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDesc();
    }

    private void setupDesc() {
        SpannableString desc = SpannableUtil.fontSpan(str_desc, str_desc_bold, font_bold);
        descTextView.setText(desc);
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
