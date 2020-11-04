package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import java.util.List;

public class MarketBrandItemVH extends BaseViewHolder<BrandDataModel> {

    @BindView(R.id.img_brand_thumb) ImageView brandThumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_brand_tag) TextView brandTagTextView;

    @BindView(R.id.img_product1_thumb) ImageView product1ThumbImageView;
    @BindView(R.id.txt_product1_brand_name) TextView product1BrandNameTextView;
    @BindView(R.id.txt_product1_name) TextView product1NameTextView;
    @BindView(R.id.layout_product1_discount_panel) LinearLayout product1DiscountPanel;
    @BindView(R.id.txt_product1_discount_percent) TextView product1DiscountPercentTextView;
    @BindView(R.id.txt_product1_price_origin) TextView product1PriceOriginTextView;
    @BindView(R.id.txt_product1_price) TextView product1PriceTextView;

    @BindView(R.id.img_product2_thumb) ImageView product2ThumbImageView;
    @BindView(R.id.txt_product2_brand_name) TextView product2BrandNameTextView;
    @BindView(R.id.txt_product2_name) TextView product2NameTextView;
    @BindView(R.id.layout_product2_discount_panel) LinearLayout product2DiscountPanel;
    @BindView(R.id.txt_product2_discount_percent) TextView product2DiscountPercentTextView;
    @BindView(R.id.txt_product2_price_origin) TextView product2PriceOriginTextView;
    @BindView(R.id.txt_product2_price) TextView product2PriceTextView;

    @BindDrawable(R.drawable.placeholder_image) Drawable placeholder_image;

    @BindString(R.string.base_price_percent) String format_percent;

    public MarketBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(brandThumbImageView);

        brandNameTextView.setText(data.getBrandName());
        brandTagTextView.setText(data.getBrandTag().replace(",", " "));

        List<ProductDataModel> products = data.getProducts();
        setupProductData(products);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupProductData(List<ProductDataModel> products) {
        ProductDataModel product1DataModel = products.get(0);
        Glide.with(context)
            .load(product1DataModel.getImageUrl())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(product1ThumbImageView);

        product1BrandNameTextView.setText(product1DataModel.getBrandName());
        product1NameTextView.setText(product1DataModel.getProductName());

        if (product1DataModel.isDiscount()) {
            product1DiscountPercentTextView.setText(
                String.format(format_percent, product1DataModel.getDiscountPercent()));
            product1PriceOriginTextView.setPaintFlags(
                product1PriceOriginTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            product1PriceOriginTextView.setText(StringUtil.toDigit(product1DataModel.getPriceOrigin()));
            product1DiscountPanel.setVisibility(View.VISIBLE);
        } else {
            product1DiscountPanel.setVisibility(View.GONE);
        }

        product1PriceTextView.setText(StringUtil.toDigit(product1DataModel.getPrice()));


        ProductDataModel product2DataModel = products.get(1);
        Glide.with(context)
            .load(product2DataModel.getImageUrl())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(product2ThumbImageView);

        product2BrandNameTextView.setText(product2DataModel.getBrandName());
        product2NameTextView.setText(product2DataModel.getProductName());

        if (product2DataModel.isDiscount()) {
            product2DiscountPercentTextView.setText(
                String.format(format_percent, product2DataModel.getDiscountPercent()));
            product2PriceOriginTextView.setPaintFlags(
                product1PriceOriginTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            product2PriceOriginTextView.setText(StringUtil.toDigit(product2DataModel.getPriceOrigin()));
            product2DiscountPanel.setVisibility(View.VISIBLE);
        } else {
            product2DiscountPanel.setVisibility(View.GONE);
        }

        product2PriceTextView.setText(StringUtil.toDigit(product2DataModel.getPrice()));
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    @OnClick(R.id.layout_product1_panel)
    void onProduct1Click() {
        int productNo = data.getProducts().get(0).getProductNo();
        ProductDetailActivity.start(context, productNo);
    }

    @OnClick(R.id.layout_product2_panel)
    void onProduct2Click() {
        int productNo = data.getProducts().get(1).getProductNo();
        ProductDetailActivity.start(context, productNo);
    }
}
