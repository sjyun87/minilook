package com.minilook.minilook.ui.market.viewholder.new_arrivals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class MarketNewArrivalsVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private ProductAdapter adapter;
    private Gson gson = new Gson();

    public MarketNewArrivalsVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_new_arrivals, (ViewGroup) itemView, false));
        setupViewPager();
    }

    private void setupViewPager() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new ProductAdapter();
        adapter.setViewType(ProductAdapter.VIEW_TYPE_BIG);
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<ProductDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, ProductDataModel.class))
            .toList()
            .blockingGet();
    }
}