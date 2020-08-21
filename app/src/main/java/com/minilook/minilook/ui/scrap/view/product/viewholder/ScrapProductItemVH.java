package com.minilook.minilook.ui.scrap.view.product.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class ScrapProductItemVH extends BaseViewHolder<CategoryDataModel> {

    public ScrapProductItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_grid, (ViewGroup) itemView, false));
    }

    @Override public void bind(CategoryDataModel $data) {
        super.bind($data);


    }

    void onItemClick(View view) {

    }
}