package com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ProductItemVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    //@BindView(R.id.txt_price_percent) TextView pricePercentTextView;
    //@BindView(R.id.txt_price) TextView priceTextView;
    //
    //@BindString(R.string.base_price_percent) String formatPercent;
    //@BindString(R.string.base_product_name) String formatProductName;

    public ProductItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getThumb())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrand().getName());
        productNameTextView.setText(data.getName());

        //if (data.id()) {
        //    pricePercentTextView.setVisibility(View.VISIBLE);
        //    pricePercentTextView.setText(String.format(formatPercent, data.getPrice_sale_percent()));
        //} else {
        //    pricePercentTextView.setVisibility(View.GONE);
        //}
        //priceTextView.setText(StringUtil.toDigit(data.getPrice_sale()));

        //
        //itemView.setOnClickListener(v -> RxBus.send(new MainPresenterImpl.RxEventNavigateToDetail(data.getWeb_url())));
    }
}
