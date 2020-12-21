package com.minilook.minilook.ui.scrapbook.view.product.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewScrapbookProductBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;

public class ScrapbookProductVH extends BaseViewHolder<ProductDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    @StringRes int str_format_percent = R.string.base_price_percent;

    private final ViewScrapbookProductBinding binding;

    public ScrapbookProductVH(@NonNull View parent) {
        super(ViewScrapbookProductBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewScrapbookProductBinding.bind(itemView);
        binding.imgScrap.setOnClickListener(view -> onScrapClick());
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

    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(false);
            data.setScrapCount(data.getScrapCount() - 1);
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateProductScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
