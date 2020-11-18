package com.minilook.minilook.ui.market.viewholder.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.market.MarketModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.market.viewholder.event.adapter.MarketEventAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import java.util.Objects;

public class MarketEventVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.layout_tab_panel) TabLayout tabLayout;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_2) int dp_2;
    @BindDimen(R.dimen.dp_48) int dp_48;

    @BindColor(R.color.color_FFFFFFFF) int color_FFFFFFFF;

    private MarketEventAdapter adapter;
    private Gson gson = new Gson();

    public MarketEventVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_event, (ViewGroup) itemView, false));

        setupTabLayout();
        setupProductRecyclerView();
    }

    private void setupTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                adapter.set(getProducts(getTabView(tab).getCode()));
                adapter.refresh();
                recyclerView.scrollToPosition(0);
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
        return (TabView) Objects.requireNonNull(tabLayout.getTabAt(position)).getCustomView();
    }

    private void setupProductRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new MarketEventAdapter();
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(context)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(recyclerView);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        MarketModuleDataModel moduleData = parseJsonToModel();
        tagTextView.setText(moduleData.getTag());

        if (tabLayout.getTabCount() == 0) {
            for (CodeDataModel tabModel : moduleData.getTabs()) {
                TabView tabView = TabView.builder()
                    .context(context)
                    .name(tabModel.getName())
                    .code(tabModel.getCode())
                    .width(dp_48)
                    .selectedTextColor(color_FFFFFFFF)
                    .build();

                TabLayout.Tab tab = tabLayout.newTab();
                tab.setCustomView(tabView);
                tabLayout.addTab(tab);
            }
            getTabView(0).setupSelected();
        }

        adapter.set(getProducts(moduleData.getTabs().get(0).getCode()));
        adapter.refresh();
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
