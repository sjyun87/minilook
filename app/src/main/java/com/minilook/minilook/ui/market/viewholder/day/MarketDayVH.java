package com.minilook.minilook.ui.market.viewholder.day;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.market.MarketModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.ViewMarketDayBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.market.viewholder.day.adapter.MarketDayAdapter;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailActivity;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Objects;

public class MarketDayVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_2 = R.dimen.dp_2;
    @DimenRes int dp_48 = R.dimen.dp_48;

    @ColorRes int color_FFFFFFFF = R.color.color_FFFFFFFF;
    @ColorRes int color_FFA8A6A1 = R.color.color_FFA8A6A1;

    private final ViewMarketDayBinding binding;
    private final Gson gson;

    private MarketDayAdapter adapter;
    private MarketModuleDataModel moduleData;

    public MarketDayVH(@NonNull View parent) {
        super(ViewMarketDayBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketDayBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupTabLayout();
        setupProductRecyclerView();
    }

    private void setupTabLayout() {
        binding.layoutTabPanel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                adapter.set(getProducts(getTabView(tab).getCode()));
                adapter.refresh();

                binding.rcvProduct.scrollToPosition(0);
                getTabView(tab).setupSelected();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {
                getTabView(tab).setupUnselected();
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private TabView getTabView(TabLayout.Tab tab) {
        return (TabView) tab.getCustomView();
    }

    private TabView getTabView(int position) {
        return (TabView) Objects.requireNonNull(binding.layoutTabPanel.getTabAt(position)).getCustomView();
    }

    private void setupProductRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketDayAdapter();
        binding.rcvProduct.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimen(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvProduct);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        if (data.isRefreshing()) resetData();

        binding.txtTitle.setText(data.getTitle());

        if (moduleData == null) moduleData = parseJsonToModel();
        binding.txtTag.setText(moduleData.getTag());

        if (binding.layoutTabPanel.getTabCount() == 0) {
            for (CodeDataModel tabModel : moduleData.getTabs()) {
                TabView tabView = TabView.builder()
                    .context(context)
                    .name(tabModel.getName())
                    .code(tabModel.getCode())
                    .width(resources.getDimen(dp_48))
                    .selectedTextColor(resources.getColor(color_FFFFFFFF))
                    .unselectedTextColor(resources.getColor(color_FFA8A6A1))
                    .build();

                TabLayout.Tab tab = binding.layoutTabPanel.newTab();
                tab.setCustomView(tabView);
                binding.layoutTabPanel.addTab(tab);
            }
            getTabView(0).setupSelected();
            adapter.set(getProducts(moduleData.getTabs().get(0).getCode()));
            adapter.refresh();
        }

        binding.imgMore.setOnClickListener(this::onMoreClick);
    }

    private void resetData() {
        moduleData = null;
        binding.layoutTabPanel.removeAllTabs();
        data.setRefreshing(false);
    }

    private List<ProductDataModel> getProducts(String code) {
        return Observable.fromIterable(moduleData.getProducts())
            .filter(model -> model.getType().equals(code))
            .toList()
            .blockingGet();
    }

    private MarketModuleDataModel parseJsonToModel() {
        return gson.fromJson(data.getData(), MarketModuleDataModel.class);
    }

    void onMoreClick(View view) {
        PromotionDetailActivity.start(context, moduleData.getPromotionNo());
    }
}
