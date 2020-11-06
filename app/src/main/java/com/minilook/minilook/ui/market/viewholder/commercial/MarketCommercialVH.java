package com.minilook.minilook.ui.market.viewholder.commercial;

import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MarketCommercialVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.vp_commercial) ViewPager2 viewPager;

    private MarketCommercialAdapter adapter;
    private Gson gson = new Gson();
    private Handler handler = new Handler();

    public MarketCommercialVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_commercial, (ViewGroup) itemView, false));
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new MarketCommercialAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                cancelAutoSlide();
                startAutoSlide();
            }

            @Override public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    cancelAutoSlide();
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    startAutoSlide();
                }
            }
        });
        ViewCompat.setNestedScrollingEnabled(viewPager, false);
    }

    private Runnable nextPageRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() != adapter.getSize() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            } else {
                viewPager.setCurrentItem(0, false);
            }
        }
    };

    private void startAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.postDelayed(nextPageRunnable, 3000);
    }

    private void cancelAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.removeCallbacks(nextPageRunnable);
    }

    @Override public void onAttach() {
        startAutoSlide();
    }

    @Override public void onDetach() {
        cancelAutoSlide();
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        List<CommercialDataModel> items = parseJsonToModel();
        viewPager.setUserInputEnabled(items.size() > 1);
        adapter.set(items);
        adapter.refresh();
    }

    private List<CommercialDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CommercialDataModel>>() {
        }.getType());
    }
}
