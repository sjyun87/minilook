package com.minilook.minilook.ui.market.viewholder.commercial.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.CommercialType;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.databinding.ViewMarketCommercialItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.event_detail.EventDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;

public class MarketCommercialItemVH extends BaseViewHolder<CommercialDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_format_index = R.string.market_commercial_index;

    private ViewMarketCommercialItemBinding binding;

    public MarketCommercialItemVH(@NonNull View parent) {
        super(ViewMarketCommercialItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketCommercialItemBinding.bind(itemView);
    }

    public void bind(CommercialDataModel $data, int position, int total) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgContents);

        binding.txtIndex.setText(String.format(resources.getString(str_format_index), position + 1, total));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        switch (CommercialType.toType(data.getType())) {
            case PROMOTION:
                PromotionDetailActivity.start(context, data.getNo());
                break;
            case EVENT:
                EventDetailActivity.start(context, data.getNo());
                break;
            case PRODUCT:
                ProductDetailActivity.start(context, data.getNo());
                break;
        }
    }
}
