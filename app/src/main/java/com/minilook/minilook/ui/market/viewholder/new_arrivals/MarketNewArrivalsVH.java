package com.minilook.minilook.ui.market.viewholder.new_arrivals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.databinding.ViewMarketNewArrivalsBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.market.viewholder.new_arrivals.adapter.MarketNewArrivalsAdapter;
import com.minilook.minilook.ui.product_bridge.ProductBridgeActivity;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Objects;

public class MarketNewArrivalsVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_2 = R.dimen.dp_2;
    @DimenRes int dp_48 = R.dimen.dp_48;

    private final ViewMarketNewArrivalsBinding binding;
    private final Gson gson;

    private MarketNewArrivalsAdapter adapter;
    private MarketModuleDataModel moduleData;

    public MarketNewArrivalsVH(@NonNull View parent) {
        super(ViewMarketNewArrivalsBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewMarketNewArrivalsBinding.bind(itemView);
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
        adapter = new MarketNewArrivalsAdapter();
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
        if (binding.layoutTabPanel.getTabCount() == 0) {
            for (CodeDataModel tabModel : moduleData.getTabs()) {
                TabView tabView = TabView.builder()
                    .context(context)
                    .name(tabModel.getName())
                    .code(tabModel.getCode())
                    .width(resources.getDimen(dp_48))
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
        MarketModuleDataModel data = parseJsonToModel();
        return Observable.fromIterable(data.getProducts())
            .filter(model -> model.getType().equals(code))
            .toList()
            .blockingGet();
    }

    private MarketModuleDataModel parseJsonToModel() {
        return gson.fromJson(data.getData(), MarketModuleDataModel.class);
    }

    void onMoreClick(View view) {
        SearchOptionDataModel options = new SearchOptionDataModel();
        options.setFilerSearch(true);
        ProductBridgeActivity.start(context, options);
    }
}
