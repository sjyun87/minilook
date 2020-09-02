package com.minilook.minilook.ui.brand_detail.viewholder;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindColor;
import butterknife.BindView;

public class BrandDetailStyleVH extends BaseViewHolder<String> {

    @BindView(R.id.img_style) ImageView imageView;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    public BrandDetailStyleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand_detail_style, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(imageView);
    }
}
