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
import com.minilook.minilook.ui.dialog.listener.OnButtonClickListener;
import com.minilook.minilook.util.SpannableUtil;
import timber.log.Timber;

public class MainMarketingNotifyDialog extends Dialog {

    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.dialog_main_marketing_desc) String str_desc;
    @BindString(R.string.dialog_main_marketing_desc_b) String str_desc_bold;

    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private Context context;
    private OnButtonClickListener listener;

    public MainMarketingNotifyDialog(@NonNull Context context, @NonNull OnButtonClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.e("onAttachedToWindow");
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_marketing);
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
        listener.onPositiveClick();
    }
}
