package com.minilook.minilook.ui.scrapbook.view.brand;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.scrapbook.view.brand.adapter.ScrapbookBrandAdapter;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;

public class ScrapbookBrandFragment extends BaseFragment implements ScrapbookBrandPresenter.View {

    public static ScrapbookBrandFragment newInstance() {
        return new ScrapbookBrandFragment();
    }

    @BindView(R.id.rcv_brand) RecyclerView recyclerView;

    private ScrapbookBrandPresenter presenter;
    private ScrapbookBrandAdapter adapter = new ScrapbookBrandAdapter();
    private BaseAdapterDataView<BrandDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrapbook_brand;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapbookBrandPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapbookBrandArguments provideArguments() {
        return ScrapbookBrandArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(layoutManager)
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(5)
                .build();
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }
}
