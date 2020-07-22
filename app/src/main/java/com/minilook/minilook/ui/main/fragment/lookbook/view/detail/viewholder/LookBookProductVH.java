package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.util.StringUtil;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class LookBookProductVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_brand_logo) ImageView brandLogoImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_price_percent) TextView pricePercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;

    @BindString(R.string.base_price_percent) String formatPercent;
    @BindString(R.string.base_product_name) String formatProductName;

    public LookBookProductVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_detail_product, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        //BrandInfoDataModel brandData = data.getBrand();
        //
        //Glide.with(itemView)
        //    .load(brandData.getLogo_url())
        //    .circleCrop()
        //    .into(brandLogoImageView);
        //
        //brandNameTextView.setText(brandData.getName());
        //
        //Glide.with(itemView)
        //    .load(data.getImage_thumb_url())
        //    .error(defaultImage)
        //    .into(thumbImageView);
        //
        //if (data.is_sale()) {
        //    pricePercentTextView.setVisibility(View.VISIBLE);
        //    pricePercentTextView.setText(String.format(formatPercent, data.getPrice_sale_percent()));
        //} else {
        //    pricePercentTextView.setVisibility(View.GONE);
        //}
        //priceTextView.setText(StringUtil.toDigit(data.getPrice_sale()));
        //nameTextView.setText(data.getName());
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToBrand(data.getBrand().getId()));
    }

    @OnClick(R.id.layout_product_panel)
    void onProductClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToDetail(data.getWeb_url()));
    }
}
