package com.minilook.minilook.ui.product_bridge;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;

public class ProductBridgeActivity extends BaseActivity implements ProductBridgePresenter.View {

    public static void start(Context context, SearchOptionDataModel model) {
        Intent intent = new Intent(context, ProductBridgeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("options", model);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    private ProductBridgePresenter presenter;
    private ProductAdapter productAdapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterView = productAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_product_bridge;
    }

    @Override protected void createPresenter() {
        presenter = new ProductBridgePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProductBridgeArguments provideArguments() {
        return ProductBridgeArguments.builder()
            .view(this)
            .options((SearchOptionDataModel) getIntent().getSerializableExtra("options"))
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        productRecyclerView.setAdapter(productAdapter);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(productRecyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        productRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void productRefresh() {
        productAdapterView.refresh();
    }

    @Override public void productRefresh(int start, int rows) {
        productAdapterView.refresh(start, rows);
    }
}
