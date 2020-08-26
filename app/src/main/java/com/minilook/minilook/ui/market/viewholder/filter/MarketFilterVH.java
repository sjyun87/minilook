package com.minilook.minilook.ui.market.viewholder.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.filter.adapter.MarketFilterAdapter;
import com.minilook.minilook.ui.search_filter.SearchFilterActivity;
import java.util.ArrayList;
import java.util.List;

public class MarketFilterVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.rcv_filter) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private MarketFilterAdapter adapter;
    private Gson gson = new Gson();

    public MarketFilterVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_filter, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        adapter = new MarketFilterAdapter();
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(recyclerView);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<CategoryDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CategoryDataModel>>() {}.getType());
    }

    @OnClick(R.id.txt_more)
    void onMoreClick() {
        SearchFilterActivity.start(context);
    }
}
