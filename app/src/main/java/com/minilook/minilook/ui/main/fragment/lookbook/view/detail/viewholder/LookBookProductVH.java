package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class LookBookProductVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_brand_logo) ImageView brandLogoImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_review) TextView reviewTextView;
    @BindView(R.id.txt_scrap) TextView scrapTextView;
    @BindView(R.id.txt_category) TextView categoryTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_product_desc) TextView descTextView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    public LookBookProductVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_detail_product, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        BrandInfoDataModel brandModel = data.getBrand();
        CategoryDataModel categoryDataModel = data.getCategory();

        Glide.with(context)
            .load(brandModel.getUrl_logo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(brandLogoImageView);

        brandNameTextView.setText(brandModel.getName());

        Glide.with(context)
            .load(data.getUrl_image())
            .into(thumbImageView);

        reviewTextView.setText(StringUtil.toDigit(data.getReview_cnt()));
        scrapTextView.setText(StringUtil.toDigit(data.getScrap_cnt()));
        categoryTextView.setText(categoryDataModel.getName());
        nameTextView.setText(data.getName());
        descTextView.setText(data.getDesc());

        itemView.setOnClickListener(v -> ProductDetailActivity.start(context, data.getId()));
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToBrandDetail(data.getBrand().getId()));
    }

    @OnClick(R.id.layout_review_panel)
    void onReviewClick() {

    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapClick() {

    }
}
