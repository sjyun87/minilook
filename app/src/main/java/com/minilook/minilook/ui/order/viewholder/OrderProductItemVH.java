package com.minilook.minilook.ui.order.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order.adapter.OrderOptionAdapter;

public class OrderProductItemVH extends BaseViewHolder<ShoppingProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.rcv_option) RecyclerView recyclerView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    private OrderOptionAdapter adapter;

    public OrderProductItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_product, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderOptionAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override public void bind(ShoppingProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getThumb_url())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        nameTextView.setText(data.getProduct_name());

        adapter.set(data.getOptions());
        adapter.refresh();
    }
}
