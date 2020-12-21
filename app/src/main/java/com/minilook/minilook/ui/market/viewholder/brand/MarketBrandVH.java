package com.minilook.minilook.ui.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.databinding.ViewMarketBrandBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand.BrandActivity;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_6 = R.dimen.dp_6;

    private final ViewMarketBrandBinding binding;
    private final Gson gson;

    private MarketBrandAdapter adapter;

    public MarketBrandVH(@NonNull View parent) {
        super(ViewMarketBrandBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketBrandBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvBrand.setHasFixedSize(true);
        binding.rcvBrand.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MarketBrandAdapter();
        binding.rcvBrand.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimen(dp_6))
            .asSpace()
            .build()
            .addTo(binding.rcvBrand);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        binding.txtTitle.setText(data.getTitle());
        binding.imgMore.setOnClickListener(this::onMoreClick);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<BrandDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
        }.getType());
    }

    void onMoreClick(View view) {
        BrandActivity.start(context);
    }
}
