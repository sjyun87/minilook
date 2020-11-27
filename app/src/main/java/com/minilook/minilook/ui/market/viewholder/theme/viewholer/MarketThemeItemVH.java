package com.minilook.minilook.ui.market.viewholder.theme.viewholer;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;

public class MarketThemeItemVH extends _BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.layout_discount_panel) LinearLayout discountPanel;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_price_percent) String format_percent;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    public MarketThemeItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_theme_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrandName());
        productNameTextView.setText(data.getProductName());

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscountPercent()));
            priceOriginTextView.setPaintFlags(priceOriginTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            priceOriginTextView.setText(StringUtil.toDigit(data.getPriceOrigin()));
            discountPanel.setVisibility(View.VISIBLE);
        } else {
            discountPanel.setVisibility(View.GONE);
        }
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }
}
