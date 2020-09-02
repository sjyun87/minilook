package com.minilook.minilook.ui.market.viewholder.commercial.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.type.CommercialType;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

import butterknife.BindView;

public class MarketCommercialItemVH extends BaseViewHolder<CommercialDataModel> {

    @BindView(R.id.img_contents) ImageView contentsImageView;

    public MarketCommercialItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_commercial_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CommercialDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage_url())
            .into(contentsImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (data.getType().equals(CommercialType.PROMOTION.getValue())) {
            PromotionDetailActivity.start(context, data.getId());
        } else if (data.getType().equals(CommercialType.EVENT.getValue())) {
            EventDetailActivity.start(context, data.getId());
        } else if (data.getType().equals(CommercialType.PRODUCT.getValue())) {
            ProductDetailActivity.start(context, data.getId());
        }
    }
}
