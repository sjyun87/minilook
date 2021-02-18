package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.databinding.ViewColorChipBinding;
import lombok.Builder;

public class ColorChip extends FrameLayout {

    private static final String COLOR_SILVER = "#C0C0C0";
    private static final String COLOR_GOLD = "#FFD700";
    private static final String COLOR_MULTI = "multi";
    private static final String COLOR_NONE = "none";

    @DrawableRes int color_silver = R.drawable.color_silver_circle;
    @DrawableRes int color_gold = R.drawable.color_gold_circle;
    @DrawableRes int color_multi = R.drawable.color_multi_circle;
    @DrawableRes int color_none = R.drawable.color_none_circle;

    private ViewColorChipBinding binding;
    private ProductStockDataModel model;

    @Builder
    public ColorChip(@NonNull Context context, @NonNull ProductStockDataModel model) {
        super(context);
        this.model = model;

        initView();
        setColor(model.getCode());
        if (model.isStock()) showSlash();
    }

    public ColorChip(@NonNull Context context) {
        super(context);
        initView();
    }

    public ColorChip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ColorChip(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ColorChip(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = ViewColorChipBinding.inflate(LayoutInflater.from(getContext()), this);
    }

    public void setColor(String color) {
        switch (color) {
            case COLOR_SILVER:
                binding.imgColor.setImageResource(color_silver);
                break;
            case COLOR_GOLD:
                binding.imgColor.setImageResource(color_gold);
                break;
            case COLOR_MULTI:
                binding.imgColor.setImageResource(color_multi);
                break;
            case COLOR_NONE:
                binding.imgColor.setImageResource(color_none);
                break;
            default:
                setColor(Color.parseColor(color));
                break;
        }
    }

    public void setColor(@ColorInt int color) {
        binding.imgColor.setImageResource(0);
        ((GradientDrawable) binding.imgColor.getBackground()).setColor(color);
    }

    public void showSlash() {
        binding.imgColor.setVisibility(VISIBLE);
    }

    public void hideSlash() {
        binding.imgColor.setVisibility(GONE);
    }
}
