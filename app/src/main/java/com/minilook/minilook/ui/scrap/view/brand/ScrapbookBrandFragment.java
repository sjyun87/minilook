package com.minilook.minilook.ui.scrap.view.brand;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.preorder.adapter.PreorderAdapter;
import com.minilook.minilook.ui.scrap.view.brand.di.ScrapBrandArguments;

public class ScrapbookBrandFragment extends BaseFragment implements ScrapBrandPresenter.View {

    public static ScrapbookBrandFragment newInstance() {
        return new ScrapbookBrandFragment();
    }

    @BindView(R.id.rcv_scrap_brand) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private ScrapBrandPresenter presenter;
    private PreorderAdapter adapter = new PreorderAdapter();
    private BaseAdapterDataView<PreorderDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_scrap_brand;
    }

    @Override protected void createPresenter() {
        presenter = new ScrapBrandPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ScrapBrandArguments provideArguments() {
        return ScrapBrandArguments.builder()
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
