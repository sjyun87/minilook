package com.minilook.minilook.ui.market.viewholder.commercial.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.code.CommercialType;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class MarketCommercialItemVH extends BaseViewHolder<CommercialDataModel> {

    @BindView(R.id.img_contents) ImageView contentsImageView;
    @BindView(R.id.txt_index) TextView indexTextView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    @BindString(R.string.market_commercial_index) String format_index;

    public MarketCommercialItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_commercial_item, (ViewGroup) itemView, false));
    }

    public void bind(CommercialDataModel $data, int position, int total) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(contentsImageView);

        indexTextView.setText(String.format(format_index, position + 1, total));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (data.getType().equals(CommercialType.PROMOTION.getValue())) {
            PromotionDetailActivity.start(context, data.getNo());
        } else if (data.getType().equals(CommercialType.EVENT.getValue())) {
            EventDetailActivity.start(context, data.getNo());
        } else if (data.getType().equals(CommercialType.PRODUCT.getValue())) {
            ProductDetailActivity.start(context, data.getNo());
        }
    }
}
