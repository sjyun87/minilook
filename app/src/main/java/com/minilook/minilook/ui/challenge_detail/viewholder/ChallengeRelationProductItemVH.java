package com.minilook.minilook.ui.challenge_detail.viewholder;

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
import com.minilook.minilook.databinding.ViewChallengeRelationProductItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;

public class ChallengeRelationProductItemVH extends BaseViewHolder<ProductDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int format_percent = R.string.base_price_percent;

    private final ViewChallengeRelationProductItemBinding binding;

    public ChallengeRelationProductItemVH(@NonNull View parent) {
        super(ViewChallengeRelationProductItemBinding.inflate(LayoutInflater.from(parent.getContext()),
            (ViewGroup) parent, false));
        binding = ViewChallengeRelationProductItemBinding.bind(itemView);
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
                String.format(resources.getString(format_percent), data.getDiscountPercent()));
            binding.txtDiscountPercent.setVisibility(View.VISIBLE);
        } else {
            binding.txtDiscountPercent.setVisibility(View.GONE);
        }
        binding.txtPrice.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }
}
