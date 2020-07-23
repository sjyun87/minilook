package com.minilook.minilook.ui.main.fragment.market.viewholder.newest;

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
import com.minilook.minilook.util.StringUtil;

public class MarketNewestItemVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_price_origin) TextView priceOriginTextView;
    @BindView(R.id.txt_price_percent) TextView pricePercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    public MarketNewestItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_big, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getUrl_thumb())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrand().getName());
        productNameTextView.setText(data.getName());

        priceOriginTextView.setText(StringUtil.toDigit(data.getPrice_origin()));
        pricePercentTextView.setText(data.getPrice_discount_percent() + "%");
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        //itemView.setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        PromotionActivity.start(context, data.getId());
        //    }
        //});
    }
}
