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
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ProductFeedVH extends _BaseViewHolder<ProductDataModel> {

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

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;
    @BindDrawable(R.drawable.ph_circle) Drawable img_placeholder_logo;
    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    public ProductFeedVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.view_lookbook_detail_product, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(brandLogoImageView);

        brandNameTextView.setText(data.getBrandName());

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        reviewTextView.setText(StringUtil.toDigit(data.getReview().getReviewCount()));
        categoryTextView.setText(data.getCategory());
        nameTextView.setText(data.getProductName());
        descTextView.setText(data.getProductDesc());

        setupScrap();

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrap() {
        if (data.isScrap()) {
            scrapImageView.setImageDrawable(img_scrap_on);
        } else {
            scrapImageView.setImageDrawable(img_scrap_off);
        }
        scrapTextView.setText(StringUtil.toDigit(data.getScrapCount()));
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    @OnClick(R.id.layout_review_panel)
    void onReviewClick() {
        // TODO Review Page 연결
    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setupScrap();
            //RxBus.send(new RxBusEvent.RxBusEventProductScrap(data.isScrap(), data));
        } else {
            LoginActivity.start(context);
        }
    }
}
