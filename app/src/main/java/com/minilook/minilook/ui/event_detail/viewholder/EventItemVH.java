package com.minilook.minilook.ui.event_detail.viewholder;

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
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;

public class EventItemVH extends _BaseViewHolder<EventDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;

    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;

    public EventItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_event, (ViewGroup) itemView, false));
    }

    @Override public void bind(EventDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getThumbUrl())
            .placeholder(img_placeholder_wide)
            .error(img_placeholder_wide)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        EventDetailActivity.start(context, data.getEventNo());
    }
}
