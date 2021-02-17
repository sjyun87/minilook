package com.minilook.minilook.ui.review_history.view.writable.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.databinding.ViewWritableReviewOptionItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import lombok.Setter;

public class WritableReviewOptionItemVH extends BaseViewHolder<OrderProductDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_format_option = R.string.review_history_written_option;

    private final ViewWritableReviewOptionItemBinding binding;
    @Setter private OnReviewWriteClickListener onReviewWriteClickListener;

    public WritableReviewOptionItemVH(@NonNull View parent) {
        super(ViewWritableReviewOptionItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewWritableReviewOptionItemBinding.bind(itemView);
        binding.layoutProductPanel.setOnClickListener(view -> onProductClick());
        binding.txtWrite.setOnClickListener(view -> onWriteClick());
    }

    @Override public void bind(OrderProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgProductThumb);

        binding.txtBrandName.setText(data.getBrandName());
        binding.txtProductName.setText(data.getProductName());
        binding.txtOption.setText(
            String.format(resources.getString(str_format_option), data.getColorName(), data.getSizeName()));
    }

    private void onProductClick() {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    private void onWriteClick() {
        if (onReviewWriteClickListener != null) onReviewWriteClickListener.onReviewClick(data);
    }

    public interface OnReviewWriteClickListener {
        void onReviewClick(OrderProductDataModel orderData);
    }
}
