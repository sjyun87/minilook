package com.minilook.minilook.ui.product_detail.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
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

public class ProductTabView extends LinearLayout {

    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_count) TextView countTextView;

    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF616161) int color_FF616161;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;

    @Getter private String name;
    @Getter private String count;

    @Builder
    public ProductTabView(@NonNull Context context, String name, String count) {
        this(context);
        this.name = name;
        this.count = count;

        initView();
        setupName(name);
    }

    public ProductTabView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_tab_priduct_detail, this));
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
    }

    public void setupName(String name) {
        nameTextView.setText(name);
    }

    public void setupSelected() {
        nameTextView.setTextColor(color_FF232323);
        nameTextView.setTypeface(font_bold);
    }

    public void setupUnselected() {
        nameTextView.setTextColor(color_FF616161);
        nameTextView.setTypeface(font_regular);
    }

    public void setupCount(String count) {
        countTextView.setText(count);
        showCount();
    }

    public void showCount() {
        countTextView.setVisibility(VISIBLE);
    }

    public void hideCount() {
        countTextView.setVisibility(GONE);
    }
}
