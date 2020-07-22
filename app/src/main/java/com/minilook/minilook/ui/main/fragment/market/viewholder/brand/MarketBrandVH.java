package com.minilook.minilook.ui.main.fragment.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.market.viewholder.brand.adapter.MarketBrandAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.core.Observable;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_subtitle) TextView subtitleTextView;
    @BindView(R.id.rcv_brand) RecyclerView recyclerView;

    private MarketBrandAdapter adapter;
    private Gson gson = new Gson();

    public MarketBrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new MarketBrandAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());
        subtitleTextView.setText(data.getSubtitle());

        List<BrandInfoDataModel> items = parseJsonToModel();
        adapter.set(items);
        adapter.refresh();
    }

    private List<BrandInfoDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, BrandInfoDataModel.class))
            .toList()
            .blockingGet();
    }
}
