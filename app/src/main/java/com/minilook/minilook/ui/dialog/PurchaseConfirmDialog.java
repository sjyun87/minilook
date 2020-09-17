package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.SpannableUtil;

public class PurchaseConfirmDialog extends BaseDialog {

    @BindView(R.id.txt_info) TextView infoTextView;

    @BindString(R.string.dialog_purchase_confirm_info) String str_info;
    @BindString(R.string.dialog_purchase_confirm_info_b) String str_info_b;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    public PurchaseConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_purchase_confirm;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupInfoSpan();
    }

    private void setupInfoSpan() {
        infoTextView.setText(SpannableUtil.foregroundColorSpan(str_info, str_info_b, color_FF8140E5));
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
