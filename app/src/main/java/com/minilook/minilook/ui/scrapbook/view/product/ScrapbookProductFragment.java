package com.minilook.minilook.ui.scrapbook.view.product;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;

public class ScrapbookProductFragment extends BaseFragment implements ScrapbookProductPresenter.View {

    public static ScrapbookProductFragment newInstance() {
        return new ScrapbookProductFragment();
    }

    @BindView(R.id.rcv_scrapbook_product) RecyclerView recyclerView;

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

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter.setViewType(ProductAdapter.VIEW_TYPE_GRID);
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh(int start, int end) {
        adapterView.refresh(start, end);
    }
}
