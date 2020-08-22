package com.minilook.minilook.ui.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.brand.BrandMenuDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandAdapter;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandMenuAdapter;
import com.minilook.minilook.ui.market.viewholder.brand.viewholder.MarketBrandMenuVH;
import java.util.ArrayList;
import java.util.List;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> implements MarketBrandMenuVH.OnMenuClickListener {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_brand) RecyclerView recyclerView;
    @BindView(R.id.vp_brand) ViewPager2 viewPager;

    private Gson gson = new Gson();
    private int selectedPosition = 0;

    private MarketBrandMenuAdapter menuAdapter;
    private MarketBrandAdapter brandAdapter;

    public MarketBrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand, (ViewGroup) itemView, false));

        setupRecyclerView();
        setupViewPager();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        menuAdapter = new MarketBrandMenuAdapter();
        recyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnMenuClickListener(this);
    }

    private void setupViewPager() {
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(2);
        brandAdapter = new MarketBrandAdapter();
        viewPager.setAdapter(brandAdapter);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        List<BrandDataModel> items = parseJsonToModel();
        menuAdapter.set(items);
        menuAdapter.refresh();
        brandAdapter.set(items);
        brandAdapter.refresh();

        viewPager.setCurrentItem(selectedPosition);
    }

    private List<BrandDataModel> parseJsonToModel() {
        List<BrandDataModel> items = gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {}.getType());
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setPosition(i);
            items.get(i).setSelect(i == 0);
        }
        return items;
    }

    @Override public void onMenuClick(int position) {
        selectedPosition = position;
        viewPager.setCurrentItem(selectedPosition, true);
    }
}
