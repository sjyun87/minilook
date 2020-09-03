package com.minilook.minilook.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.util.SpannableUtil;

public class MarketingDialog extends Dialog {

    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.dialog_marketing_desc) String str_desc;
    @BindString(R.string.dialog_marketing_desc_b) String str_desc_bold;

    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private Context context;
    private OnDialogClickListener listener;

    public MarketingDialog(@NonNull Context context, @NonNull OnDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_marketing);
        ButterKnife.bind(this);

        setupDesc();
    }

    private void setupDesc() {
        SpannableString desc = SpannableUtil.fontSpan(str_desc, str_desc_bold, font_bold);
        descTextView.setText(desc);
    }

    @OnClick(R.id.txt_later)
    void onLaterClick() {
        this.dismiss();
    }

    @OnClick(R.id.txt_agree)
    void onAgreeClick() {
        this.dismiss();
        listener.onPositiveClick();
    }
}
