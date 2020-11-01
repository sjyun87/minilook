package com.minilook.minilook.ui.market.viewholder.preorder;

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
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.preorder.adapter.MarketPreorderAdapter;
import com.minilook.minilook.ui.market.viewholder.recommend.adapter.MarketRecommendAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketPreorderVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_preorder) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private MarketPreorderAdapter adapter;
    private Gson gson = new Gson();

    public MarketPreorderVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_preorder, (ViewGroup) itemView, false));

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketPreorderAdapter();
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(dp_4)
            .asSpace()
            .build()
            .addTo(recyclerView);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        //adapter.set(parseJsonToModel());
        //adapter.refresh();
    }

    //private List<ProductDataModel> parseJsonToModel() {
    //    return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
    //    }.getType());
    //}
}
