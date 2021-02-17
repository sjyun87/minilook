package com.minilook.minilook.ui.lookbook.view.detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewLookbookDetailProductBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.review.ReviewActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class LookBookProductVH extends BaseViewHolder<ProductDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int ph_circle = R.drawable.ph_circle;
    @DrawableRes int scrap_off = R.drawable.ic_scrap_off;
    @DrawableRes int scrap_on = R.drawable.ic_scrap_on;

    @ColorRes int color_FFDBDBDB = R.color.color_FFDBDBDB;

    private final ViewLookbookDetailProductBinding binding;

    public LookBookProductVH(@NonNull View parent) {
        super(ViewLookbookDetailProductBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewLookbookDetailProductBinding.bind(itemView);
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(ph_circle)
            .error(ph_circle)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1),
                    resources.getColor(color_FFDBDBDB))))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgBrandLogo);

        binding.txtBrandName.setText(data.getBrandName());

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgProductThumb);

        //binding.txtReview.setText(StringUtil.toDigit(data.getReview().getReviewCount()));
        binding.txtCategory.setText(data.getCategory());
        binding.txtProductName.setText(data.getProductName());
        binding.txtProductDesc.setText(data.getProductDesc());
        setScrap();

        itemView.setOnClickListener(this::onItemClick);
        binding.layoutBrandPanel.setOnClickListener(view -> onBrandClick());
        binding.layoutReviewPanel.setOnClickListener(view -> onReviewClick());
        binding.layoutScrapPanel.setOnClickListener(view -> onScrapClick());
    }

    private void setScrap() {
        if (data.isScrap()) {
            binding.imgScrap.setImageResource(scrap_on);
        } else {
            binding.imgScrap.setImageResource(scrap_off);
        }
        binding.txtScrap.setText(StringUtil.toDigit(data.getScrapCount()));
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    void onBrandClick() {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    void onReviewClick() {
        ReviewActivity.start(context, data.getProductNo());
    }

    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setScrap();
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateProductScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
