package com.minilook.minilook.ui.market.viewholder.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.filter.adapter.MarketFilterAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class MarketFilterVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.rcv_filter) RecyclerView recyclerView;

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
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<CategoryDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, CategoryDataModel.class))
            .toList()
            .blockingGet();
    }

    @OnClick(R.id.txt_more)
    void onMoreClick() {

    }
}
