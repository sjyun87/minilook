package com.minilook.minilook.ui.main.fragment.market.viewholder.promotion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.promotion.PromotionActivity;

import butterknife.BindView;

public class MarketPromotionItemVH extends BaseViewHolder<PromotionDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.txt_title) TextView titleTextView;

    public MarketPromotionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_promotion_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(PromotionDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getImage_thumb_url())
            .into(thumbImageView);

        descTextView.setText(data.getDesc());
        titleTextView.setText(data.getTitle());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                PromotionActivity.start(context, data.getId());
            }
        });
    }
}
