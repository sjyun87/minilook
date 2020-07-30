package com.minilook.minilook.ui.market.viewholder.promotion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.promotion.adapter.MarketPromotionAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class MarketPromotionVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.vp_promotion) ViewPager2 viewPager;
    @BindView(R.id.indicator) DotsIndicator indicator;

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
        viewPager.setOffscreenPageLimit(2);
        indicator.setViewPager2(viewPager);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private List<PromotionDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, PromotionDataModel.class))
            .toList()
            .blockingGet();
    }
}
