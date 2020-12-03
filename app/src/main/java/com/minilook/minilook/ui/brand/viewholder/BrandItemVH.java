package com.minilook.minilook.ui.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewBrandItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandItemVH extends BaseViewHolder<BrandDataModel> {

    @DrawableRes int img_scrap_off = R.drawable.ic_scrap_off;
    @DrawableRes int img_scrap_on = R.drawable.ic_scrap_on;
    @DrawableRes int ph_circle = R.drawable.ph_circle;

    @ColorRes int color_FFDBDBDB = R.color.color_FFDBDBDB;

    private final ViewBrandItemBinding binding;

    public BrandItemVH(@NonNull View parent) {
        super(ViewBrandItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewBrandItemBinding.bind(itemView);
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(ph_circle)
            .error(ph_circle)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1),
                    ContextCompat.getColor(context, color_FFDBDBDB))))
            .into(binding.imgLogo);

        binding.txtName.setText(data.getBrandName());
        binding.txtTag.setText(data.getBrandTag().replace(",", " "));

        setupScrap();
        binding.imgScrap.setOnClickListener(this::onScrapClick);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrap() {
        if (data.isScrap()) {
            binding.imgScrap.setImageResource(img_scrap_on);
        } else {
            binding.imgScrap.setImageResource(img_scrap_off);
        }
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    void onScrapClick(View view) {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setupScrap();
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateBrandScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
