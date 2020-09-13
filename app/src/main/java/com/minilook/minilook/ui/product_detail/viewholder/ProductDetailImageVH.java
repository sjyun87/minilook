package com.minilook.minilook.ui.product_detail.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ProductDetailImageVH extends BaseViewHolder<String> {

    @BindView(R.id.img_style) ImageView imageView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    public ProductDetailImageVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_detail_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(imageView);
    }
}
