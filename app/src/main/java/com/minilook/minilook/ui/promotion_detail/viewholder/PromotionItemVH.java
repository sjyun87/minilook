package com.minilook.minilook.ui.promotion_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PromotionItemVH extends BaseViewHolder<ProductDataModel> {

    //@BindView(R.id.img_product_thumb) ImageView thumbImageView;
    //@BindView(R.id.txt_price_percent) TextView pricePercentTextView;
    //@BindView(R.id.txt_price) TextView priceTextView;
    //@BindView(R.id.txt_product_name) TextView nameTextView;

    //@BindString(R.string.base_price_percent) String formatPercent;
    //@BindString(R.string.base_product_name) String formatProductName;

    public PromotionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_grid, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

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
        //nameTextView.setText(String.format(formatProductName, data.getBrand().getName(), data.getName()));
        //
        //itemView.setOnClickListener(v -> RxBus.send(new MainPresenterImpl.RxEventNavigateToDetail(data.getWeb_url())));
    }
}
