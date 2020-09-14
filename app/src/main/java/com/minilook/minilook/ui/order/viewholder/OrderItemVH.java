package com.minilook.minilook.ui.order.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order.adapter.OrderProductAdapter;
import com.minilook.minilook.util.StringUtil;

public class OrderItemVH extends BaseViewHolder<ShoppingBrandDataModel> {

    @BindView(R.id.txt_brand_name) TextView nameTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.txt_shipping_price) TextView shippingPriceTextView;
    @BindView(R.id.txt_island_shipping_price) TextView islandShippingPriceTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;

    @BindString(R.string.order_product_shipping_free) String str_shipping_free;
    @BindString(R.string.order_product_island_shipping_price) String format_island_shipping_price;

    private OrderProductAdapter productAdapter;

    public OrderItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        productAdapter = new OrderProductAdapter();
        recyclerView.setAdapter(productAdapter);
    }

    @Override public void bind(ShoppingBrandDataModel $data) {
        super.bind($data);

        nameTextView.setText(data.getBrand_name());

        productAdapter.set(data.getProducts());
        productAdapter.refresh();

        if (data.isFreeShipping()) {
            shippingPriceTextView.setText(str_shipping_free);
        } else {
            shippingPriceTextView.setText(StringUtil.toDigit(data.getFinal_shipping_price()));
        }
        if (data.isIsland()) {
            islandShippingPriceTextView.setText(
                String.format(format_island_shipping_price, StringUtil.toDigit(data.getIsland_shipping_price())));
            islandShippingPriceTextView.setVisibility(View.VISIBLE);
        } else {
            islandShippingPriceTextView.setVisibility(View.GONE);
        }
        totalPriceTextView.setText(StringUtil.toDigit(
            data.getTotal_products_price() + data.getFinal_shipping_price() + data.getIsland_shipping_price()));
    }
}