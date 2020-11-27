package com.minilook.minilook.ui.lookbook.view.preview.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LookBookImageModuleVH extends _BaseViewHolder<String> {

    @BindView(R.id.img_lookbook) ImageView imageView;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_module_image, (ViewGroup) itemView, false));
    }

    public void bind(String $data, int position) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .addListener(new RequestListener<Drawable>() {
                @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                    boolean isFirstResource) {
                    return false;
                }

                @Override public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                    DataSource dataSource, boolean isFirstResource) {
                    if (position == 0) RxBus.send(new RxBusEventLookBookReady());
                    return false;
                }
            })
            .into(imageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToDetail(true));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventLookBookReady {
    }
}
