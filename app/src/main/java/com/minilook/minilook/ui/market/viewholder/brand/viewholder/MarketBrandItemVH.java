package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class MarketBrandItemVH extends BaseViewHolder<BrandDataModel> {

    public MarketBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        //Glide.with(context)
        //    .load(data.getThumbUrl())
        //    .placeholder(img_placeholder)
        //    .error(img_placeholder)
        //    .transition(new DrawableTransitionOptions().crossFade())
        //    .into(thumbImageView);
        //
        //startDateTextView.setText(getStartDate(data.getStartDate()));
        //brandTextView.setText(data.getBrandName());
        //titleTextView.setText(data.getTitle());
        //descTextView.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {

    }
}
