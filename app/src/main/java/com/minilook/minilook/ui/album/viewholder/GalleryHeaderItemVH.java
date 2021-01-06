package com.minilook.minilook.ui.album.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ViewGalleryHeaderItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class GalleryHeaderItemVH extends BaseViewHolder<String> {

    private final ViewGalleryHeaderItemBinding binding;

    public GalleryHeaderItemVH(@NonNull View parent) {
        super(ViewGalleryHeaderItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewGalleryHeaderItemBinding.bind(itemView);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
    }
}
