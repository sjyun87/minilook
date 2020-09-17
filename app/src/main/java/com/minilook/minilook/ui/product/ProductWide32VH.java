package com.minilook.minilook.ui.product;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order_detail.OrderDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import lombok.Setter;

public class ProductWide32VH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    public ProductWide32VH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_wide_32, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage_url())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrand_name());
        productNameTextView.setText(data.getProduct_name());
    }
}
