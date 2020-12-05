package com.minilook.minilook.ui.market.viewholder.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.ViewMarketRecommendBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.recommend.adapter.MarketRecommendAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketRecommendVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_4 = R.dimen.dp_4;

    private final ViewMarketRecommendBinding binding;
    private final Gson gson;

    private MarketRecommendAdapter adapter;

    public MarketRecommendVH(@NonNull View parent) {
        super(ViewMarketRecommendBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketRecommendBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketRecommendAdapter();
        binding.rcvProduct.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimensionPixelSize(dp_4))
            .asSpace()
            .build()
            .addTo(binding.rcvProduct);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        binding.txtTitle.setText(data.getTitle());

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<ProductDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
        }.getType());
    }
}
