package com.minilook.minilook.ui.preorder.view.close;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.preorder.view.close.adapter.PreorderCloseAdapter;
import com.minilook.minilook.ui.preorder.view.close.di.PreorderCloseArguments;

public class PreorderCloseFragment extends _BaseFragment implements PreorderClosePresenter.View {

    public static PreorderCloseFragment newInstance() {
        return new PreorderCloseFragment();
    }

    @BindView(R.id.rcv_preorder_close) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private PreorderClosePresenter presenter;
    private PreorderCloseAdapter adapter = new PreorderCloseAdapter();
    private BaseAdapterDataView<PreorderDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_preorder_close;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderClosePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderCloseArguments provideArguments() {
        return PreorderCloseArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(dp_4)
            .asSpace()
            .build()
            .addTo(recyclerView);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(recyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }
}
