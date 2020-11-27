package com.minilook.minilook.ui.market.viewholder.category.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MarketCategoryItemVH extends _BaseViewHolder<CodeDataModel> {

    @BindView(R.id.img_icon) ImageView iconImageView;
    @BindView(R.id.txt_name) TextView nameTextView;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    public MarketCategoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_category_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getIconUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(iconImageView);

        nameTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxBusEventMarketCategoryClick(data));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventMarketCategoryClick {
        private CodeDataModel categoryData;
    }
}
