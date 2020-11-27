package com.minilook.minilook.ui.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseDialog;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import lombok.Setter;

public class BrandCallDialog extends BaseDialog {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_cs_time) TextView csTimeTextView;

    @BindString(R.string.dialog_brand_call_title) String format_title;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindFont(R.font.nanum_square_eb) Typeface font_extrabold;

    @BindDrawable(R.drawable.ph_circle) Drawable img_placeholder_logo;

    @Setter private String brandName;
    @Setter private String brandLogo;
    @Setter private String csTimeInfo;

    public BrandCallDialog(@NonNull Context context) {
        super(context);
    }

    @Override protected int getLayoutID() {
        return R.layout.dialog_brand_call;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = String.format(format_title, brandName);
        titleTextView.setText(SpannableUtil.fontSpan(title, brandName, font_extrabold));

        Glide.with(getContext())
            .load(brandLogo)
            .placeholder(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(getContext(), 1), color_FFDBDBDB)))
            .into(logoImageView);

        if (!TextUtils.isEmpty(csTimeInfo)) {
            csTimeTextView.setText(csTimeInfo);
        } else {
            csTimeTextView.setVisibility(View.GONE);
        }
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
