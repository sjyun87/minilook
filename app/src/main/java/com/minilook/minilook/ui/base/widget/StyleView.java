package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.StyleDataModel;
import lombok.Builder;

public class StyleView extends FrameLayout {

    @BindView(R.id.txt_style) TextView styleTextView;

    @BindDrawable(R.drawable.bg_filter_button_off) Drawable bg_button_off;
    @BindDrawable(R.drawable.bg_filter_button_on) Drawable bg_button_on;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindString(R.string.base_tag) String format_tag;

    private StyleDataModel model;

    @Builder
    public StyleView(@NonNull Context context, @NonNull StyleDataModel model) {
        this(context);
        this.model = model;

        initView();
        setupSize(model.getName());
    }

    public StyleView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_style_view, this));
    }

    public void setupSize(String name) {
        styleTextView.setText(String.format(format_tag, name));
    }

    public void selected() {
        styleTextView.setBackground(bg_button_on);
        styleTextView.setTextColor(color_FF8140E5);
        styleTextView.setTypeface(font_bold);
    }

    public void unselected() {
        styleTextView.setBackground(bg_button_off);
        styleTextView.setTextColor(color_FF232323);
        styleTextView.setTypeface(font_regular);
    }
}
