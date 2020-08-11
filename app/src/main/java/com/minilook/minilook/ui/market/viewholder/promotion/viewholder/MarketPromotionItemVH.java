package com.minilook.minilook.ui.market.viewholder.promotion.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class MarketPromotionItemVH extends BaseViewHolder<PromotionDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_desc) TextView descTextView;

    public MarketPromotionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_promotion_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(PromotionDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getUrl_thumb())
            .into(thumbImageView);

        titleTextView.setText(data.getTitle());
        descTextView.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {

    }
}
