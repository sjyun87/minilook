package com.minilook.minilook.ui.scrap.view.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ScrapBrandItemVH extends BaseViewHolder<CategoryDataModel> {

    public ScrapBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_scrap_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);


    }

    void onItemClick(View view) {

    }
}
