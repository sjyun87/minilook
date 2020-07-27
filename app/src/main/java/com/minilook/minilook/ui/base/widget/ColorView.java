package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.base.ColorDataModel;
import lombok.Builder;

public class ColorView extends FrameLayout {

    @BindView(R.id.img_color) ImageView colorImageView;
    @BindView(R.id.img_slash) ImageView slashImageView;

    private ColorDataModel colorDataModel;

    @Builder
    public ColorView(@NonNull Context context, @NonNull ColorDataModel model) {
        this(context);
        this.colorDataModel = model;

        initView();
        setupColor(model.getColor());
        if (model.isSoldout()) showSlash();
    }

    public ColorView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_color_view, this));
    }

    public void setupColor(String color) {
        ((GradientDrawable) colorImageView.getBackground()).setColor(Color.parseColor(color));
    }

    public void setupColor(int color) {
        ((GradientDrawable) colorImageView.getBackground()).setColor(color);
    }

    public void showSlash() {
        slashImageView.setVisibility(VISIBLE);
    }

    public void hideSlash() {
        slashImageView.setVisibility(GONE);
    }
}
