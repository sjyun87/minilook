package com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.model.market.MarketMDPickDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick.adapter.CategoryAdapter;
import com.minilook.minilook.ui.main.fragment.market.viewholder.mdpick.adapter.ProductAdapter;
import io.reactivex.rxjava3.core.Observable;
import java.util.ArrayList;
import java.util.List;

public class MarketMDPickVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_category) RecyclerView categoryRecyclerView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private Gson gson = new Gson();

    private List<MarketMDPickDataModel> items;
    private List<CategoryDataModel> categoryList;
    private int selectedPosition = 0;

    public MarketMDPickVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_mdpick, (ViewGroup) itemView, false));
        setupCategoryRecyclerView();
        setupProductRecyclerView();
    }

    private void setupCategoryRecyclerView() {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnItemClickListener(this::onItemClick);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        productAdapter = new ProductAdapter();
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        items = parseJsonToModel();
        categoryList = parseToCategoryList(items);
        setupCategoryList();
        setupProductList();
    }

    private void setupCategoryList() {
        categoryAdapter.set(categoryList);
        categoryAdapter.refresh();
    }

    private void setupProductList() {
        productAdapter.set(items.get(selectedPosition).getProducts());
        productAdapter.refresh();
    }

    private List<MarketMDPickDataModel> parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, MarketMDPickDataModel.class))
            .toList()
            .blockingGet();
    }

    private List<CategoryDataModel> parseToCategoryList(List<MarketMDPickDataModel> items) {
        List<CategoryDataModel> categoryList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            CategoryDataModel model = new CategoryDataModel();
            model.setName(items.get(i).getCategory());
            model.setPosition(i);
            model.setSelect(i == 0);
            categoryList.add(model);
        }
        return categoryList;
    }

    public void onItemClick(int position) {
        categoryAdapter.get(selectedPosition).setSelect(false);
        selectedPosition = position;
        categoryAdapter.get(selectedPosition).setSelect(true);
        categoryAdapter.refresh();
        setupProductList();
    }
}
