package com.minilook.minilook.ui.market.viewholder.trend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
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
import com.minilook.minilook.databinding.ViewMarketTrendBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.market.viewholder.trend.adapter.MarketTrendAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Objects;

public class MarketTrendVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_2 = R.dimen.dp_2;
    @DimenRes int dp_48 = R.dimen.dp_48;

    private final ViewMarketTrendBinding binding;
    private final Gson gson;

    private MarketTrendAdapter adapter;

    public MarketTrendVH(@NonNull View parent) {
        super(ViewMarketTrendBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketTrendBinding.bind(itemView);
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
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketTrendAdapter();
        binding.rcvProduct.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(resources.getDimensionPixelSize(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvProduct);
        ViewCompat.setNestedScrollingEnabled(binding.rcvProduct, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        binding.txtTitle.setText(data.getTitle());

        MarketModuleDataModel moduleData = parseJsonToModel();
        binding.txtTag.setText(moduleData.getTag());

        if (binding.layoutTabPanel.getTabCount() == 0) {
            for (CodeDataModel tabModel : moduleData.getTabs()) {
                TabView tabView = TabView.builder()
                    .context(context)
                    .name(tabModel.getName())
                    .code(tabModel.getCode())
                    .width(resources.getDimensionPixelSize(dp_48))
                    .build();

                TabLayout.Tab tab = binding.layoutTabPanel.newTab();
                tab.setCustomView(tabView);
                binding.layoutTabPanel.addTab(tab);
            }
            getTabView(0).setupSelected();
            adapter.set(getProducts(moduleData.getTabs().get(0).getCode()));
            adapter.refresh();
        }
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
}
