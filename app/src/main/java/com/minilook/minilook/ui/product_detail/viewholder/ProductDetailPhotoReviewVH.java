package com.minilook.minilook.ui.product_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.databinding.ViewProductDetailPhotoReviewItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class ProductDetailPhotoReviewVH extends BaseViewHolder<ImageDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewProductDetailPhotoReviewItemBinding binding;

    @Setter private OnPhotoClickListener onPhotoClickListener;

    public ProductDetailPhotoReviewVH(@NonNull View parent) {
        super(ViewProductDetailPhotoReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),
            (ViewGroup) parent, false));
        binding = ViewProductDetailPhotoReviewItemBinding.bind(itemView);
    }

    @Override public void bind(int $position, ImageDataModel $data) {
        super.bind($position, $data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgPhoto);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        if (onPhotoClickListener != null) onPhotoClickListener.onPhotoClick(position);
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }
}
