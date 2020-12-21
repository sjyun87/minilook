package com.minilook.minilook.ui.market.viewholder.trend.viewholder;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.ViewMarketTrendItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;

public class MarketTrendItemVH extends BaseViewHolder<ProductDataModel> {

    @StringRes int str_format_percent = R.string.base_price_percent;

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewMarketTrendItemBinding binding;

    public MarketTrendItemVH(@NonNull View parent) {
        super(ViewMarketTrendItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketTrendItemBinding.bind(itemView);
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());

        if (data.isDiscount()) {
            binding.txtDiscountPercent.setText(
                String.format(resources.getString(str_format_percent), data.getDiscountPercent()));
            binding.txtPriceOrigin.setPaintFlags(binding.txtPriceOrigin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.txtPriceOrigin.setText(StringUtil.toDigit(data.getPriceOrigin()));
            binding.layoutDiscountPanel.setVisibility(View.VISIBLE);
        } else {
            binding.layoutDiscountPanel.setVisibility(View.GONE);
        }
        binding.txtPrice.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }
}
