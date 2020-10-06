package com.minilook.minilook.ui.order_detail.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order_detail.adapter.OrderDetailGoodsAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class OrderDetailItemVH extends BaseViewHolder<OrderBrandDataModel> {

    @BindView(R.id.img_brand_logo) ImageView brandLogoImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.rcv_goods) RecyclerView recyclerView;
    @BindView(R.id.txt_shipping_price) TextView shippingPriceTextView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;

    @BindString(R.string.order_detail_free_shipping) String str_free_shipping;

    private OrderDetailGoodsAdapter adapter;

    public OrderDetailItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_detail, (ViewGroup) itemView, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderDetailGoodsAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override public void bind(OrderBrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrandLogo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(brandLogoImageView);

        brandNameTextView.setText(data.getBrandName());

        adapter.set(setupBrandDate(data.getGoods()));
        adapter.refresh();

        shippingPriceTextView.setText(
            data.getShippingPrice() != 0 ? StringUtil.toDigit(data.getShippingPrice()) : str_free_shipping);
    }

    private List<OrderProductDataModel> setupBrandDate(List<OrderProductDataModel> goods) {
        for (OrderProductDataModel item : goods) {
            item.setBrandName(data.getBrandName());
        }
        return goods;
    }
}
