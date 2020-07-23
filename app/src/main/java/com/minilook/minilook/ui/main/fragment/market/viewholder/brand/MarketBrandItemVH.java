package com.minilook.minilook.ui.main.fragment.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.MainPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

public class MarketBrandItemVH extends BaseViewHolder<BrandInfoDataModel> {

    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;

    public MarketBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandInfoDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getUrl_logo())
            .circleCrop()
            .into(logoImageView);
        nameTextView.setText(data.getName());
        tagTextView.setText(data.getTag());

        itemView.setOnClickListener(v -> RxBus.send(new MainPresenterImpl.RxEventNavigateToBrandDetail(data.getId())));
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {

    }
}
