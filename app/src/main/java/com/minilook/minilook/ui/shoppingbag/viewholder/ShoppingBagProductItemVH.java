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
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagPresenterImpl;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagOptionAdapter;

public class ShoppingBagProductItemVH extends BaseViewHolder<ShoppingProductDataModel> {

    @BindView(R.id.img_checkbox) ImageView checkImageView;
    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.rcv_option) RecyclerView recyclerView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.ic_checkbox2_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox2_on) Drawable img_check_on;

    @BindDimen(R.dimen.dp_4) int dp_4;

    @BindString(R.string.shoppingbag_display_soldout) String str_soldout;
    @BindString(R.string.shoppingbag_display_selling_stop) String str_selling_stop;

    private ShoppingBagOptionAdapter adapter;

    public ShoppingBagProductItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shoppingbag_product, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ShoppingBagOptionAdapter();
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

        setupCheckBox();

        int displayCode = data.getDisplay_code();
        if (displayCode == DisplayCode.DISPLAY.getValue()) {
            displayLabelTextView.setVisibility(View.GONE);
            adapter.set(data.getOptions());
            adapter.refresh();
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            displayLabelTextView.setVisibility(View.VISIBLE);
            if (displayCode == DisplayCode.SOLD_OUT.getValue()) {
                displayLabelTextView.setText(str_soldout);
            } else {
                displayLabelTextView.setText(str_selling_stop);
            }
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void setupCheckBox() {
        if (data.isSelected()) {
            checkImageView.setImageDrawable(img_check_on);
        } else {
            checkImageView.setImageDrawable(img_check_off);
        }
    }

    @OnClick(R.id.img_checkbox)
    void onCheckBox() {
        data.setSelected(!data.isSelected());
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventProductCheckedChanged(data.getBrand_id()));
    }

    @OnClick(R.id.layout_product_panel)
    void onProductClick(){
        ProductDetailActivity.start(context, data.getProduct_id());
    }
}
