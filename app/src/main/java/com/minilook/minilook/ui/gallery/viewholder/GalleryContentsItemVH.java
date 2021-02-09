package com.minilook.minilook.ui.gallery.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewGalleryContentsItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GalleryContentsItemVH extends BaseViewHolder<PhotoDataModel> {

    @DrawableRes int img_select = R.drawable.bg_gallery_select;
    @DrawableRes int img_unselect = R.drawable.bg_gallery_unselect;
    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewGalleryContentsItemBinding binding;

    public GalleryContentsItemVH(@NonNull View parent) {
        super(ViewGalleryContentsItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewGalleryContentsItemBinding.bind(itemView);
    }

    @Override public void bind(PhotoDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getUriPath())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        if (data.isSelect()) {
            binding.imgCheck.setImageResource(img_select);
            binding.txtSelectPosition.setText(String.valueOf(data.getSelectPosition()));
            binding.txtSelectPosition.setVisibility(View.VISIBLE);
        } else {
            binding.imgCheck.setImageResource(img_unselect);
            binding.txtSelectPosition.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxEventGalleryImageClick(data));
    }

    @AllArgsConstructor @Getter public final static class RxEventGalleryImageClick {
        private final PhotoDataModel model;
    }
}
