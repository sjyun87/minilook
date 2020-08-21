package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class TabView extends LinearLayout {

    @BindView(R.id.txt_name) TextView nameTextView;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF616161) int color_FF616161;
    @BindColor(R.color.color_00000000) int color_00000000;
    @BindColor(R.color.color_FFF5F6F9) int color_FFF5F6F9;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_eb) Typeface font_extrabold;

    @Getter private String name;
    private int width;

    @Builder
    public TabView(@NonNull Context context, String name, int width) {
        this(context);
        this.name = name;
        this.width = width;

        initView();
        setupName();
        setupWidth();
    }

    public TabView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_tab_view, this));
        setLayoutParams(
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setupName() {
        nameTextView.setText(name);
    }

    private void setupWidth() {
        if (width != 0) nameTextView.setWidth(width);
    }

    public void setupSelected() {
        nameTextView.setTextColor(color_FF232323);
        nameTextView.setTypeface(font_extrabold);
    }

    public void setupUnselected() {
        nameTextView.setTextColor(color_FF616161);
        nameTextView.setTypeface(font_bold);
    }
}
