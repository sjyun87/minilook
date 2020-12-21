package com.minilook.minilook.ui.lookbook.view.preview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewLookbookModuleImageBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;

public class LookBookImageModuleVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewLookbookModuleImageBinding binding;

    public LookBookImageModuleVH(@NonNull View parent) {
        super(ViewLookbookModuleImageBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewLookbookModuleImageBinding.bind(itemView);
    }

    @Override
    public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgLookbook);
        binding.imgLookbook.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToDetail(true));
    }
}
