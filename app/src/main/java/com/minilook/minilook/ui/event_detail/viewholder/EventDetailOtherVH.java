package com.minilook.minilook.ui.event_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.databinding.ViewEventDetailOtherBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;

public class EventDetailOtherVH extends BaseViewHolder<EventDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_148 = R.dimen.dp_148;

    private final ViewEventDetailOtherBinding binding;

    public EventDetailOtherVH(@NonNull View parent, boolean isOnlyOne) {
        super(ViewEventDetailOtherBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewEventDetailOtherBinding.bind(itemView);

        setupItemSize(isOnlyOne);
    }

    private void setupItemSize(boolean isOnlyOne) {
        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        params.width = isOnlyOne ? ViewGroup.LayoutParams.MATCH_PARENT : resources.getDimen(dp_148);
        binding.getRoot().setLayoutParams(params);
    }

    @Override public void bind(EventDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        EventDetailActivity.start(context, data.getEventNo());
    }
}
