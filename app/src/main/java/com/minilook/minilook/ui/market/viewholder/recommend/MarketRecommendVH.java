package com.minilook.minilook.ui.market.viewholder.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.recommend.adapter.MarketRecommendOptionAdapter;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class MarketRecommendVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;
    @BindView(R.id.rcv_option) RecyclerView optionRecyclerView;

    private final int view_count;

    private ProductAdapter productAdapter;
    private MarketRecommendOptionAdapter optionAdapter;
    private Gson gson = new Gson();

    public MarketRecommendVH(@NonNull View itemView, int view_count) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_recommend, (ViewGroup) itemView, false));
        this.view_count = view_count;

        setupProductRecyclerView();
        setupOptionRecyclerView();
    }

    private void setupProductRecyclerView() {
        int spanCount = view_count % 2 == 0 ? 2 : 3;
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        productRecyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter();
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_IMAGE);
        productRecyclerView.setAdapter(productAdapter);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (view_count == 5) {
                    layoutManager.setSpanCount(6);
                    return position == 0 ? 3 : 2;
                }
                return 1;
            }
        });
    }

    private void setupOptionRecyclerView() {
        optionRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        optionRecyclerView.setAdapter(optionAdapter);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        productAdapter.set(parseJsonToModel());
        productAdapter.refresh();
    }

    private List<ProductDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, ProductDataModel.class))
            .toList()
            .blockingGet();
    }

    @OnClick(R.id.img_more)
    void onMoreClick() {

    }
}
