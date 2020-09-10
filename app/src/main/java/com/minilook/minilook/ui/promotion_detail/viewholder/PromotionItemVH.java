package com.minilook.minilook.ui.promotion_detail.viewholder;

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
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class PromotionItemVH extends BaseViewHolder<PromotionDataModel> {

    @BindView(R.id.img_promotion) ImageView thumbImageView;

    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;

    public PromotionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_promotion, (ViewGroup) itemView, false));
    }

    @Override public void bind(PromotionDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getThumb_url())
            .placeholder(img_placeholder_wide)
            .error(img_placeholder_wide)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        PromotionDetailActivity.start(context, data.getPromotion_id());
    }
}
