package com.minilook.minilook.ui.preorder.view.open;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.preorder.view.open.adapter.PreorderOpenAdapter;
import com.minilook.minilook.ui.preorder.view.open.di.PreorderOpenArguments;

public class PreorderOpenFragment extends BaseFragment implements PreorderOpenPresenter.View {

    public static PreorderOpenFragment newInstance() {
        return new PreorderOpenFragment();
    }

    @BindView(R.id.rcv_preorder_open) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) ConstraintLayout emptyPanel;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private PreorderOpenPresenter presenter;
    private PreorderOpenAdapter adapter = new PreorderOpenAdapter();
    private BaseAdapterDataView<PreorderDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_preorder_open;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderOpenPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderOpenArguments provideArguments() {
        return PreorderOpenArguments.builder()
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
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }
}
