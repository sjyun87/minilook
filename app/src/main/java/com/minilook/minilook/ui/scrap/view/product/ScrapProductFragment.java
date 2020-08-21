package com.minilook.minilook.ui.scrap.view.product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.preorder.adapter.PreorderAdapter;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrap.view.product.di.ScrapProductArguments;

public class ScrapProductFragment extends BaseFragment implements ScrapProductPresenter.View {

    public static ScrapProductFragment newInstance() {
        return new ScrapProductFragment();
    }

    @BindView(R.id.rcv_scrap_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private ScrapProductPresenter presenter;
    private ProductAdapter adapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrap_product;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapProductPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapProductArguments provideArguments() {
        return ScrapProductArguments.builder()
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