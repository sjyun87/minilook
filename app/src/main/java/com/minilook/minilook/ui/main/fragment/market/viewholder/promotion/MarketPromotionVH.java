package com.minilook.minilook.ui.main.fragment.market.viewholder.promotion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.data.model.market.MarketModuleDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.market.viewholder.promotion.adapter.MarketPromotionAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.core.Observable;
import me.relex.circleindicator.CircleIndicator3;

public class MarketPromotionVH extends BaseViewHolder<MarketModuleDataModel> {

    @BindView(R.id.vp_promotion) ViewPager2 viewPager;
    @BindView(R.id.indicator) CircleIndicator3 indicator;

    private MarketPromotionAdapter adapter;
    private Gson gson = new Gson();

    public MarketPromotionVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_promotion, (ViewGroup) itemView, false));
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new MarketPromotionAdapter();
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    }

    @Override public void bind(MarketModuleDataModel $data) {
        super.bind($data);

        List<PromotionDataModel> items = parseJsonToModel();
        adapter.set(items);
        adapter.refresh();
    }

    private List<PromotionDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getDatas())
            .map(json -> gson.fromJson(json, PromotionDataModel.class))
            .toList()
            .blockingGet();
    }
}
