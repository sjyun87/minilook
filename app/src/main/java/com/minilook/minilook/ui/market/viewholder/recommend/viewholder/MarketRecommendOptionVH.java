package com.minilook.minilook.ui.market.viewholder.recommend.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class MarketRecommendOptionVH extends BaseViewHolder<String> {

    @BindView(R.id.txt_option) TextView optionTextView;

    public MarketRecommendOptionVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_recommend_option, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        optionTextView.setText(data);
    }
}
