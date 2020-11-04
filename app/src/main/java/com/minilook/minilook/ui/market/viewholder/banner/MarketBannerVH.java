package com.minilook.minilook.ui.market.viewholder.banner;

import android.os.Handler;
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
    private Handler handler = new Handler();

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
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                handler.removeCallbacks(nextPageRunnable);
                handler.postDelayed(nextPageRunnable, 3000);
            }

            @Override public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    handler.removeCallbacks(nextPageRunnable);
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.postDelayed(nextPageRunnable, 3000);
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
