package com.minilook.minilook.ui.market.viewholder.new_arrivals.viewholder;

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
import com.minilook.minilook.databinding.ViewMarketNewArrivalsItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;

public class MarketNewArrivalsItemVH extends BaseViewHolder<ProductDataModel> {

    @StringRes int str_format_percent = R.string.base_percent;

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewMarketNewArrivalsItemBinding binding;

    public MarketNewArrivalsItemVH(@NonNull View parent) {
        super(ViewMarketNewArrivalsItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketNewArrivalsItemBinding.bind(itemView);
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
