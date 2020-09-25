package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import lombok.Builder;

public class ColorView extends FrameLayout {

    private static final String COLOR_SILVER = "#C0C0C0";
    private static final String COLOR_GOLD = "#FFD700";
    private static final String COLOR_MULTI = "multi";

    @BindView(R.id.img_color) ImageView colorImageView;
    @BindView(R.id.img_slash) ImageView slashImageView;

    @BindDrawable(R.drawable.color_silver_circle) Drawable color_silver;
    @BindDrawable(R.drawable.color_gold_circle) Drawable color_gold;
    @BindDrawable(R.drawable.color_multi_circle) Drawable color_multi;

    private ProductStockDataModel model;

    @Builder
    public ColorView(@NonNull Context context, @NonNull ProductStockDataModel model) {
        super(context);
        this.model = model;

        initView();
        setupColor(model.getCode());
        if (model.isStock()) showSlash();
    }

    public ColorView(@NonNull Context context) {
        super(context);
        initView();
    }

    public ColorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ColorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ColorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_color_view, this));
    }

    public void setupColor(String color) {
        switch (color) {
            case COLOR_SILVER:
                colorImageView.setImageDrawable(color_silver);
                break;
            case COLOR_GOLD:
                colorImageView.setImageDrawable(color_gold);
                break;
            case COLOR_MULTI:
                colorImageView.setImageDrawable(color_multi);
                break;
            default:
                ((GradientDrawable) colorImageView.getBackground()).setColor(Color.parseColor(color));
                break;
        }
    }

    public void setupColor(@ColorInt int color) {
        ((GradientDrawable) colorImageView.getBackground()).setColor(color);
    }

    public void showSlash() {
        slashImageView.setVisibility(VISIBLE);
    }

    public void hideSlash() {
        slashImageView.setVisibility(GONE);
    }
}
