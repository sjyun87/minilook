package com.minilook.minilook.ui.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandMenuDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class BrandVH extends BaseViewHolder<BrandMenuDataModel> {

    public BrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandMenuDataModel $data) {
        super.bind($data);



        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {

    }
}
