package com.minilook.minilook.ui.shoppingbag.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ShoppingProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagGoodsAdapter;

public class ShoppingBagProductItemVH extends BaseViewHolder<ShoppingProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.rcv_goods) RecyclerView recyclerView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private ShoppingBagGoodsAdapter adapter;

    public ShoppingBagProductItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shoppingbag_product, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShoppingBagGoodsAdapter();
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(dp_4)
            .asSpace()
            .build()
            .addTo(recyclerView);
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

        adapter.set(data.getGoods());
        adapter.refresh();
    }
}
