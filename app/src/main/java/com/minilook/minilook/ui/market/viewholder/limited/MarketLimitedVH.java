package com.minilook.minilook.ui.market.viewholder.limited;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindFont;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.util.SpannableUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.minilook.minilook.ui.product.adapter.ProductAdapter.VIEW_TYPE_FULL;

public class MarketLimitedVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_2) int dp_2;
    @BindFont(R.font.nanum_square_eb) Typeface font_eb;

    private ProductAdapter adapter;
    private Gson gson = new Gson();

    public MarketLimitedVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_limited, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter = new ProductAdapter();
        adapter.setViewType(VIEW_TYPE_FULL);
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

        titleTextView.setText(getBoldText());

        adapter.set(parseJsonToModel());
        adapter.refresh();
    }

    private SpannableString getBoldText() {
        SpannableString title = new SpannableString(data.getTitle());
        StringTokenizer tokenizer = new StringTokenizer(data.getBold_text(), ",");
        while (tokenizer.hasMoreTokens()) {
            SpannableUtil.fontSpan(title, tokenizer.nextToken(), font_eb);
        }
        return title;
    }

    private List<ProductDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
        }.getType());
    }
}
