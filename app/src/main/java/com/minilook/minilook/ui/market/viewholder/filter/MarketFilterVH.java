package com.minilook.minilook.ui.market.viewholder.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;

public class MarketFilterVH extends BaseViewHolder<MarketDataModel> {

    private Gson gson = new Gson();

    public MarketFilterVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_filter, (ViewGroup) itemView, false));
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);
    }

    private List<PromotionDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, PromotionDataModel.class))
            .toList()
            .blockingGet();
    }
}
