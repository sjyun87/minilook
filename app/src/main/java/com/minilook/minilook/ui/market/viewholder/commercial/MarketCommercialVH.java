package com.minilook.minilook.ui.market.viewholder.commercial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.commercial.adapter.MarketCommercialAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MarketCommercialVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.vp_commercial) ViewPager2 viewPager;

    private MarketCommercialAdapter adapter;
    private Gson gson = new Gson();

    public MarketCommercialVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_commercial, (ViewGroup) itemView, false));
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new MarketCommercialAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        ViewCompat.setNestedScrollingEnabled(viewPager, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<CommercialDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CommercialDataModel>>() {
        }.getType());
    }
}
