package com.minilook.minilook.ui.market.viewholder.theme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.market.MarketModuleDataModel;
import com.minilook.minilook.databinding.ViewMarketThemeBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.TagView;
import com.minilook.minilook.ui.market.viewholder.theme.adapter.MarketThemeAdapter;

public class MarketThemeVH extends BaseViewHolder<MarketDataModel> {

    @DimenRes int dp_2 = R.dimen.dp_2;

    private final ViewMarketThemeBinding binding;
    private final Gson gson;

    private MarketThemeAdapter adapter;

    public MarketThemeVH(@NonNull View parent) {
        super(ViewMarketThemeBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewMarketThemeBinding.bind(itemView);
        gson = App.getInstance().getGson();

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new MarketThemeAdapter();
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

        MarketModuleDataModel model = parseJsonToModel();
        binding.layoutThemePanel.removeAllViews();
        for (String tag : model.getTags()) {
            TagView tagView = TagView.builder()
                .context(context)
                .tag(tag)
                .build();
            binding.layoutThemePanel.addView(tagView);
        }
        adapter.set(model.getProducts());
        adapter.refresh();
    }

    private MarketModuleDataModel parseJsonToModel() {
        return gson.fromJson(data.getData(), MarketModuleDataModel.class);
    }
}
