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
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.databinding.ViewMarketCommercialBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.commercial.adapter.MarketCommercialAdapter;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketCommercialVH extends BaseViewHolder<MarketDataModel> {

    private final ViewMarketCommercialBinding binding;
    private final Gson gson;
    private final Handler handler;

    private MarketCommercialAdapter adapter;

    public MarketCommercialVH(@NonNull View parent) {
        super(ViewMarketCommercialBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketCommercialBinding.bind(itemView);
        gson = App.getInstance().getGson();
        handler = new Handler();

        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new MarketCommercialAdapter();
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(2);
        ViewCompat.setNestedScrollingEnabled(binding.viewpager, false);
    }

    private void startAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.postDelayed(nextPageRunnable, 3000);
    }

    private void cancelAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.removeCallbacks(nextPageRunnable);
    }

    @Override public void onAttach() {
        binding.viewpager.registerOnPageChangeCallback(callback);
        startAutoSlide();
    }

    @Override public void onDetach() {
        binding.viewpager.unregisterOnPageChangeCallback(callback);
        cancelAutoSlide();
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        List<CommercialDataModel> items = parseJsonToModel();
        binding.viewpager.setUserInputEnabled(items.size() > 1);

        if (adapter.getRealItemCount() == 0) {
            adapter.set(items);
            adapter.refresh();
        }
    }

    private List<CommercialDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<CommercialDataModel>>() {
        }.getType());
    }

    private final ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
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
    };

    private final Runnable nextPageRunnable = new Runnable() {
        @Override
        public void run() {
            if (binding.viewpager.getCurrentItem() != adapter.getSize() - 1) {
                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1, true);
            } else {
                binding.viewpager.setCurrentItem(0, false);
            }
        }
    };
}
