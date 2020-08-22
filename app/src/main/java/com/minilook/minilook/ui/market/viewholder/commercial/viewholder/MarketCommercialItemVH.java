package com.minilook.minilook.ui.market.viewholder.commercial.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class MarketCommercialItemVH extends BaseViewHolder<CommercialDataModel> {

    @BindView(R.id.img_contents) ImageView contentsImageView;

    public MarketCommercialItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_commercial_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(CommercialDataModel $data) {
        super.bind($data);

        //Glide.with(context)
        //    .load(data.getImage_url())
        //    .into(contentsImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        // 타입별 분기 처리
    }
}
