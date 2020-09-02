package com.minilook.minilook.ui.market.viewholder.recommend;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import butterknife.BindFont;
import butterknife.BindView;
import butterknife.OnClick;

public class MarketRecommendVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    @BindFont(R.font.nanum_square_eb) Typeface font_extrabold;

    private final int view_count;

    private ProductAdapter productAdapter;
    private Gson gson = new Gson();

    public MarketRecommendVH(@NonNull View itemView, int view_count) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_recommend, (ViewGroup) itemView, false));
        this.view_count = view_count;

        setupProductRecyclerView();
    }

    private void setupProductRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 6);
        productRecyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter();
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_IMAGE);
        productRecyclerView.setAdapter(productAdapter);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                if (view_count == 5) {
                    return position < 2 ? 3 : 2;
                } else if (view_count % 2 == 0) {
                    return 3;
                } else {
                    return 2;
                }
            }
        });
        ViewCompat.setNestedScrollingEnabled(productRecyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(getBoldText());

        List<ProductDataModel> items = parseJsonToModel();
        productAdapter.set(items.subList(0, view_count));
        productAdapter.refresh();
    }

    private SpannableString getBoldText() {
        SpannableString title = new SpannableString(data.getTitle());
        StringTokenizer tokenizer = new StringTokenizer(data.getBold_text(), ",");
        while (tokenizer.hasMoreTokens()) {
            SpannableUtil.fontSpan(title, tokenizer.nextToken(), font_extrabold);
        }
        return title;
    }

    private List<ProductDataModel> parseJsonToModel() {
        return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
        }.getType());
    }

    @OnClick(R.id.img_more)
    void onMoreClick() {
        //TODO : 전체보기
    }
}
