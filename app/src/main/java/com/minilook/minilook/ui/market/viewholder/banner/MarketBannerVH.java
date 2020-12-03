package com.minilook.minilook.ui.market.viewholder.banner;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.databinding.ViewMarketBannerBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.banner.adapter.MarketBannerAdapter;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketBannerVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_4 = R.dimen.dp_4;

    private final ViewMarketBannerBinding binding;
    private final Gson gson;
    private final Handler handler;
    private MarketBannerAdapter adapter;

    public MarketBannerVH(@NonNull View parent) {
        super(ViewMarketBannerBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketBannerBinding.bind(itemView);
        gson = App.getInstance().getGson();
        handler = new Handler();

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        adapter = new MarketBannerAdapter();
        binding.vpBanner.setAdapter(adapter);
        binding.vpBanner.setOffscreenPageLimit(2);
        binding.vpBanner.setPageTransformer(new MarginPageTransformer(resources.getDimensionPixelSize(dp_4)));
    }

    private void startAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.postDelayed(nextPageRunnable, 3000);
    }

    private void cancelAutoSlide() {
        if (adapter.getRealItemCount() > 1) handler.removeCallbacks(nextPageRunnable);
    }

    @Override public void onAttach() {
        binding.vpBanner.registerOnPageChangeCallback(callback);
        startAutoSlide();
    }

    @Override public void onDetach() {
        binding.vpBanner.unregisterOnPageChangeCallback(callback);
        cancelAutoSlide();
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        List<CommercialDataModel> items = parseJsonToModel();
        binding.vpBanner.setUserInputEnabled(items.size() > 1);

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
            if (binding.vpBanner.getCurrentItem() != adapter.getSize() - 1) {
                binding.vpBanner.setCurrentItem(binding.vpBanner.getCurrentItem() + 1, true);
            } else {
                binding.vpBanner.setCurrentItem(0, false);
            }
        }
    };
}
