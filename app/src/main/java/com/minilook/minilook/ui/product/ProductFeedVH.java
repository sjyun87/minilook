package com.minilook.minilook.ui.product;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ProductFeedVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_brand_logo) ImageView brandLogoImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_review) TextView reviewTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_scrap) TextView scrapTextView;
    @BindView(R.id.txt_category) TextView categoryTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_product_desc) TextView descTextView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;
    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    public ProductFeedVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_feed, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrand_logo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(brandLogoImageView);

        brandNameTextView.setText(data.getBrand_name());

        Glide.with(context)
            .load(data.getImage_url())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        reviewTextView.setText(StringUtil.toDigit(data.getReview_cnt()));
        scrapTextView.setText(StringUtil.toDigit(data.getScrap_cnt()));
        categoryTextView.setText(data.getCategory());
        nameTextView.setText(data.getProduct_name());
        descTextView.setText(data.getProduct_desc());

        setupScrapImage();

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrapImage() {
        if (data.isScrap()) {
            scrapImageView.setImageDrawable(img_scrap_on);
        } else {
            scrapImageView.setImageDrawable(img_scrap_off);
        }
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProduct_id());
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        BrandDetailActivity.start(context, data.getBrand_id());
    }

    @OnClick(R.id.layout_review_panel)
    void onReviewClick() {

    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            setupScrapImage();
            RxBus.send(new RxBusEvent.RxBusEventProductScrap(data.isScrap(), data));
        } else {
            LoginActivity.start(context);
        }
    }
}
