package com.minilook.minilook.ui.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandAdapter;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_brand) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_6) int dp_6;

    private Gson gson = new Gson();

    private MarketBrandAdapter menuAdapter;

    public MarketBrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand, (ViewGroup) itemView, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        menuAdapter = new MarketBrandAdapter();
        recyclerView.setAdapter(menuAdapter);
        DividerDecoration.builder(context)
            .size(dp_6)
            .asSpace()
            .build()
            .addTo(recyclerView);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        //titleTextView.setText(getBoldText());
        //
        //brandItems = parseJsonToModel();
        //
        //menuAdapter.set(brandItems);
        //menuAdapter.refresh();
        //
        //setupBrandData(brandItems.get(selectedPosition));
    }
}
