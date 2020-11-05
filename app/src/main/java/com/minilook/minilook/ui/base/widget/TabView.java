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
    @Getter private String code;
    private int unselectedTextColor = color_FF616161;
    private int selectedTextColor = color_FF232323;
    private Typeface unselectedTextFont = font_bold;
    private Typeface selectedTextFont = font_extrabold;

    @Builder
    public TabView(@NonNull Context context, @NonNull String name, String code,
        int width, int unselectedTextColor, int selectedTextColor,
        Typeface unselectedTextFont, Typeface selectedTextFont) {

        super(context);
        this.name = name;
        this.code = code;
        this.unselectedTextColor = unselectedTextColor;
        this.selectedTextColor = selectedTextColor;
        this.unselectedTextFont = unselectedTextFont;
        this.selectedTextFont = selectedTextFont;

        initView();
        setupName();
        if (width != 0) setupWidth(width);
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

    private void setupWidth(int width) {
        nameTextView.setWidth(width);
    }

    public void setupSelected() {
        nameTextView.setTextColor(selectedTextColor != 0 ? selectedTextColor : color_FF232323);
        nameTextView.setTypeface(selectedTextFont != null ? selectedTextFont : font_extrabold);
    }

    public void setupUnselected() {
        nameTextView.setTextColor(unselectedTextColor != 0 ? unselectedTextColor : color_FF616161);
        nameTextView.setTypeface(unselectedTextFont != null ? unselectedTextFont : font_bold);
    }
}
