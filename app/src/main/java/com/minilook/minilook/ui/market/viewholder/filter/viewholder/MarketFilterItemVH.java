package com.minilook.minilook.ui.market.viewholder.filter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindView;

public class MarketFilterItemVH extends BaseViewHolder<CategoryDataModel> {

    @BindView(R.id.img_icon) ImageView iconImageView;
    @BindView(R.id.txt_name) TextView nameTextView;

    public MarketFilterItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_filter_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage_url())
            .into(iconImageView);

        nameTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        //TODO : 카테고리 필터 검색 기능
    }
}
