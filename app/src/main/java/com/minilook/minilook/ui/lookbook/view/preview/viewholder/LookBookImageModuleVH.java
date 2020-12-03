package com.minilook.minilook.ui.lookbook.view.preview.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewLookbookModuleImageBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.main.MainPresenterImpl;

public class LookBookImageModuleVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewLookbookModuleImageBinding binding;

    public LookBookImageModuleVH(@NonNull View parent) {
        super(ViewLookbookModuleImageBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewLookbookModuleImageBinding.bind(itemView);
    }

    @Override
    public void bind(int $position, String $data) {
        super.bind($data);

        RequestBuilder<Drawable> builder =
            Glide.with(context)
                .load(data)
                .placeholder(ph_square)
                .error(ph_square)
                .transition(new DrawableTransitionOptions().crossFade());
        if ($position == 0) builder.listener(glideListener);
        builder.into(binding.imgLookbook);

        binding.imgLookbook.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToDetail(true));
    }

    private final RequestListener<Drawable> glideListener = new RequestListener<Drawable>() {
        @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
            boolean isFirstResource) {
            return false;
        }

        @Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
            DataSource dataSource, boolean isFirstResource) {
            RxBus.send(new MainPresenterImpl.RxBusEventLookBookReady());
            return false;
        }
    };
}
