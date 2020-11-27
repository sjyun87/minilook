package com.minilook.minilook.ui.scrapbook.view.product;

import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;

public class ScrapbookProductFragment extends _BaseFragment implements ScrapbookProductPresenter.View {

    public static ScrapbookProductFragment newInstance() {
        return new ScrapbookProductFragment();
    }

    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    private ScrapbookProductPresenter presenter;
    private ProductAdapter adapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrapbook_product;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapbookProductPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapbookProductArguments provideArguments() {
        return ScrapbookProductArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void onProductScrap(boolean isScrap, ProductDataModel product) {
        presenter.onProductScrap(isScrap, product);
    }

    @Override public void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        recyclerView.setAdapter(adapter);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(layoutManager)
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txt_empty)
    void onEmptyClick() {
        presenter.onEmptyClick();
    }
}
