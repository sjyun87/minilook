package com.minilook.minilook.ui.order.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.order.adapter.OrderProductAdapter;
import com.minilook.minilook.util.StringUtil;

public class OrderItemVH extends _BaseViewHolder<ShoppingBrandDataModel> {

    @BindView(R.id.txt_brand_name) TextView nameTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.txt_shipping_price) TextView shippingPriceTextView;
    @BindView(R.id.txt_island_shipping_price) TextView islandShippingPriceTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;
    @BindView(R.id.edit_memo) EditText memoEditText;

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

        nameTextView.setText(data.getBrandName());

        productAdapter.set(data.getProducts());
        productAdapter.refresh();

        if (data.isFreeShipping()) {
            shippingPriceTextView.setText(str_shipping_free);
        } else {
            shippingPriceTextView.setText(StringUtil.toDigit(data.getFinalShippingPrice()));
        }
        if (data.isIsland()) {
            islandShippingPriceTextView.setText(
                String.format(format_island_shipping_price, StringUtil.toDigit(data.getIslandShippingPrice())));
            islandShippingPriceTextView.setVisibility(View.VISIBLE);
        } else {
            islandShippingPriceTextView.setVisibility(View.GONE);
        }
        totalPriceTextView.setText(StringUtil.toDigit(
            data.getTotalProductsPrice() + data.getFinalShippingPrice() + data.getIslandShippingPrice()));

        memoEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                data.setOrderMemo(s.toString().trim());
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });
    }
}
