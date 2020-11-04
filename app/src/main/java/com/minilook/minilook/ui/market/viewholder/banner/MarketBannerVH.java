package com.minilook.minilook.ui.market.viewholder.banner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.banner.adapter.MarketBannerAdapter;
import com.minilook.minilook.ui.market.viewholder.recommend.adapter.MarketRecommendAdapter;
import java.util.ArrayList;
import java.util.List;

public class MarketBannerVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.vp_banner) ViewPager2 viewPager;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private MarketBannerAdapter adapter;
    private Gson gson = new Gson();

    public MarketBannerVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_banner, (ViewGroup) itemView, false));

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        adapter = new MarketBannerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(new MarginPageTransformer(dp_4));
        ViewCompat.setNestedScrollingEnabled(viewPager, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);
        List<CommercialDataModel> items = parseJsonToModel();
        items.add(items.get(0));
        items.add(items.get(0));
        items.add(items.get(0));

        adapter.set(items);
        adapter.refresh();
    }

    private List<CommercialDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CommercialDataModel>>() {
        }.getType());
    }
}
