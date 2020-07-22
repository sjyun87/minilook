package com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick.adapter.MarketRecommendAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.core.Observable;

public class MarketMDPickVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_recommend) RecyclerView recommendRecyclerView;

    private MarketRecommendAdapter adapter;
    private Gson gson = new Gson();

    public MarketMDPickVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_recommend, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new MarketRecommendAdapter();
        recommendRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recommendRecyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimensionPixelSize(R.dimen.dp_10))
            .asSpace()
            .build()
            .addTo(recommendRecyclerView);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        List<ProductDataModel> items = parseJsonToModel();
        adapter.set(items);
        adapter.refresh();
    }

    private List<ProductDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, ProductDataModel.class))
            .toList()
            .blockingGet();
    }
}
