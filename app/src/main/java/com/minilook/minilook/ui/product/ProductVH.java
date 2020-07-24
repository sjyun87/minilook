package com.minilook.minilook.ui.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;

public class ProductVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_price_percent) TextView pricePercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_price_percent) String format_percent;

    public ProductVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getUrl_thumb())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrand().getName());
        productNameTextView.setText(data.getName());

        if (data.is_discount()) {
            pricePercentTextView.setText(String.format(format_percent, data.getPrice_discount_percent()));
            pricePercentTextView.setVisibility(View.VISIBLE);
        } else {
            pricePercentTextView.setVisibility(View.GONE);
        }

        priceTextView.setText(StringUtil.toDigit(data.getPrice()));
    }
}
