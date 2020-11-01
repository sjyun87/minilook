package com.minilook.minilook.ui.market.viewholder.banner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.recommend.adapter.MarketRecommendAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketBannerVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.vp_banner) ViewPager2 viewPager;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private MarketRecommendAdapter adapter;
    private Gson gson = new Gson();

    public MarketBannerVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_banner, (ViewGroup) itemView, false));

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        //recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        //adapter = new MarketRecommendAdapter();
        //recyclerView.setAdapter(adapter);
        //DividerDecoration.builder(context)
        //    .size(dp_4)
        //    .asSpace()
        //    .build()
        //    .addTo(recyclerView);
        //ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        //titleTextView.setText(data.getTitle());
        //
        //adapter.set(parseJsonToModel());
        //adapter.refresh();
    }

    private List<ProductDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
        }.getType());
    }
}
