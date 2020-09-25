package com.minilook.minilook.ui.market.viewholder.filter.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindView;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;

public class MarketFilterItemVH extends BaseViewHolder<CategoryDataModel> {

    @BindView(R.id.img_icon) ImageView iconImageView;
    @BindView(R.id.txt_name) TextView nameTextView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    public MarketFilterItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_filter_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getIconUrl())
            .transition(new DrawableTransitionOptions().crossFade())
            .into(iconImageView);

        nameTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        ProductBridgeActivity.start(context, getOptionData());
    }

    private SearchOptionDataModel getOptionData() {
        SearchOptionDataModel model = new SearchOptionDataModel();
        model.setCategory_code(data.getCode());
        model.setCategory_name(data.getName());
        return model;
    }
}
