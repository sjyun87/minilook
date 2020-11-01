package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

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

    public MarketBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        //Glide.with(context)
        //    .load(data.getThumbUrl())
        //    .placeholder(img_placeholder)
        //    .error(img_placeholder)
        //    .transition(new DrawableTransitionOptions().crossFade())
        //    .into(thumbImageView);
        //
        //startDateTextView.setText(getStartDate(data.getStartDate()));
        //brandTextView.setText(data.getBrandName());
        //titleTextView.setText(data.getTitle());
        //descTextView.setText(data.getDesc());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {

    }
}
