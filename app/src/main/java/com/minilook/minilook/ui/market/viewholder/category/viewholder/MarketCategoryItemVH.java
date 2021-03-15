package com.minilook.minilook.ui.market.viewholder.category.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.databinding.ViewMarketCategoryItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;

public class MarketCategoryItemVH extends BaseViewHolder<CodeDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewMarketCategoryItemBinding binding;

    public MarketCategoryItemVH(@NonNull View parent) {
        super(ViewMarketCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketCategoryItemBinding.bind(itemView);
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getIconUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgIcon);

        binding.txtName.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        SearchOptionDataModel options = new SearchOptionDataModel();
        options.setCategoryCode(data.getCode());
        options.setFilerSearch(true);
        ProductBridgeActivity.start(context, options);
    }
}
