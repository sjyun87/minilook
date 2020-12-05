package com.minilook.minilook.ui.brand_detail.viewholder;

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
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewBrandDetailProductItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import timber.log.Timber;

public class BrandDetailProductVH extends BaseViewHolder<ProductDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int img_scrap_on = R.drawable.ic_scrap_on;
    @DrawableRes int img_scrap_off = R.drawable.ic_scrap_off;

    @StringRes int str_format_percent = R.string.base_price_percent;

    private final ViewBrandDetailProductItemBinding binding;

    public BrandDetailProductVH(@NonNull View parent) {
        super(ViewBrandDetailProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewBrandDetailProductItemBinding.bind(itemView);
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

        binding.txtName.setText(data.getProductName());

        if (data.getDisplayCode() != 0) {
            if (data.getDisplayCode() == DisplayCode.DISPLAY.getValue()) {
                hideDisplayLabel();
            } else {
                showDisplayLabel();
                binding.txtDisplayLabel.setText(data.getDisplayLabel());
            }
        }

        if (data.isDiscount()) {
            binding.txtDiscountPercent.setText(
                String.format(resources.getString(str_format_percent), data.getDiscountPercent()));
            binding.txtDiscountPercent.setVisibility(View.VISIBLE);
        } else {
            binding.txtDiscountPercent.setVisibility(View.GONE);
        }
        binding.txtPrice.setText(StringUtil.toDigit(data.getPrice()));

        setupScrap();

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrap() {
        if (data.isScrap()) {
            binding.imgScrap.setImageResource(img_scrap_on);
        } else {
            binding.imgScrap.setImageResource(img_scrap_off);
        }
    }

    private void showDisplayLabel() {
        binding.curtain.setVisibility(View.VISIBLE);
        binding.txtDisplayLabel.setVisibility(View.VISIBLE);
    }

    private void hideDisplayLabel() {
        binding.curtain.setVisibility(View.GONE);
        binding.txtDisplayLabel.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setupScrap();
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateProductScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
