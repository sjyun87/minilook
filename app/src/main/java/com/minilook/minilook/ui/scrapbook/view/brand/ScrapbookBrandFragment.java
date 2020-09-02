package com.minilook.minilook.ui.scrapbook.view.brand;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.scrapbook.view.brand.adapter.ScrapbookBrandAdapter;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;

public class ScrapbookBrandFragment extends BaseFragment implements ScrapBrandPresenter.View {

    public static ScrapbookBrandFragment newInstance() {
        return new ScrapbookBrandFragment();
    }

    @BindView(R.id.rcv_scrap_brand) RecyclerView recyclerView;

    private ScrapBrandPresenter presenter;
    private ScrapbookBrandAdapter adapter = new ScrapbookBrandAdapter();
    private BaseAdapterDataView<BrandDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrap_brand;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapBrandPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapbookBrandArguments provideArguments() {
        return ScrapbookBrandArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh(int start, int end) {
        adapterView.refresh(start, end);
    }
}
