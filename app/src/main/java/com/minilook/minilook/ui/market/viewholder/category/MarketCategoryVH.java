package com.minilook.minilook.ui.market.viewholder.category;

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
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.databinding.ViewMarketCategoryBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.category.adapter.MarketCategoryAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketCategoryVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_5 = R.dimen.dp_5;

    private final ViewMarketCategoryBinding binding;
    private final Gson gson;

    private MarketCategoryAdapter adapter;

    public MarketCategoryVH(@NonNull View parent) {
        super(ViewMarketCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketCategoryBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.rcvCategory.setHasFixedSize(true);
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketCategoryAdapter();
        binding.rcvCategory.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimensionPixelSize(dp_5))
            .asSpace()
            .build()
            .addTo(binding.rcvCategory);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<CodeDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CodeDataModel>>() {
        }.getType());
    }
}
