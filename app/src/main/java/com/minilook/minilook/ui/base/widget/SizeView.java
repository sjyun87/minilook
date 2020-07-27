package com.minilook.minilook.ui.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.base.SizeDataModel;
import lombok.Builder;

public class SizeView extends FrameLayout {

    @BindView(R.id.txt_size) TextView sizeTextView;
    @BindView(R.id.img_slash) ImageView slashImageView;

    private SizeDataModel sizeDataModel;

    @Builder
    public SizeView(@NonNull Context context, @NonNull SizeDataModel model) {
        this(context);
        this.sizeDataModel = model;

        initView();
        setupSize(model.getName());
        if (model.isSoldout()) showSlash();
    }

    public SizeView(@NonNull Context context) {
        super(context);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_size_view, this));
    }

    public void setupSize(String size) {
        sizeTextView.setText(size);
    }
    
    public void showSlash() {
        slashImageView.setVisibility(VISIBLE);
    }

    public void hideSlash() {
        slashImageView.setVisibility(GONE);
    }
}
