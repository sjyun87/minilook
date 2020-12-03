package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.databinding.ViewMarketBrandItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandProductAdapter;

public class MarketBrandItemVH extends BaseViewHolder<BrandDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewMarketBrandItemBinding binding;
    private MarketBrandProductAdapter adapter;

    public MarketBrandItemVH(@NonNull View parent) {
        super(ViewMarketBrandItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketBrandItemBinding.bind(itemView);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new MarketBrandProductAdapter();
        binding.rcvProduct.setAdapter(adapter);
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        binding.txtName.setText(data.getBrandName());
        binding.txtTag.setText(data.getBrandTag().replace(",", " "));

        adapter.set(data.getProducts().subList(0, 2));
        adapter.refresh();

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }
}
