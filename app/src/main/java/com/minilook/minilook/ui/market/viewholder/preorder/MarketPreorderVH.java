package com.minilook.minilook.ui.market.viewholder.preorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.databinding.ViewMarketPreorderBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.preorder.adapter.MarketPreorderAdapter;
import com.minilook.minilook.ui.preorder.PreorderActivity;
import java.util.ArrayList;
import java.util.List;

public class MarketPreorderVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_4 = R.dimen.dp_4;

    private final ViewMarketPreorderBinding binding;
    private final Gson gson;

    private MarketPreorderAdapter adapter;

    public MarketPreorderVH(@NonNull View parent) {
        super(ViewMarketPreorderBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketPreorderBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        binding.rcvPreorder.setHasFixedSize(true);
        binding.rcvPreorder.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketPreorderAdapter();
        binding.rcvPreorder.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimen(dp_4))
            .asSpace()
            .build()
            .addTo(binding.rcvPreorder);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        binding.txtTitle.setText(data.getTitle());

        adapter.set(parseJsonToModel());
        adapter.refresh();

        binding.imgMore.setOnClickListener(this::onMoreClick);
    }

    private List<PreorderDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<PreorderDataModel>>() {
        }.getType());
    }

    void onMoreClick(View view) {
        PreorderActivity.start(context);
    }
}
