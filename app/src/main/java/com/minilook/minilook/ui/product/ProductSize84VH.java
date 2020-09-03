package com.minilook.minilook.ui.product;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductSize84VH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_price_percent) String format_percent;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    @Setter private boolean isShowScrap;
    @Setter private boolean isShowBrand;

    public ProductSize84VH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_size_84, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        if (isShowScrap) {
            showScrap();
        } else {
            hideScrap();
        }

        Glide.with(context)
            .load(data.getImage_url())
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrand_name());
        productNameTextView.setText(data.getProduct_name());

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscount_percent()));
            discountPercentTextView.setVisibility(View.VISIBLE);
        } else {
            discountPercentTextView.setVisibility(View.GONE);
        }
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    public void showScrap() {
        scrapImageView.setVisibility(View.VISIBLE);
    }

    public void hideScrap() {
        scrapImageView.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProduct_id());
    }
}
