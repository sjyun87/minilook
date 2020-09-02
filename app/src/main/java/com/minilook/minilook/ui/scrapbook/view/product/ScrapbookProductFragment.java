package com.minilook.minilook.ui.scrapbook.view.product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;

public class ScrapbookProductFragment extends BaseFragment implements ScrapProductPresenter.View {

    public static ScrapbookProductFragment newInstance() {
        return new ScrapbookProductFragment();
    }

    @BindView(R.id.rcv_scrapbook_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private ScrapProductPresenter presenter;
    private ProductAdapter adapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrapbook_product;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapProductPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapbookProductArguments provideArguments() {
        return ScrapbookProductArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
