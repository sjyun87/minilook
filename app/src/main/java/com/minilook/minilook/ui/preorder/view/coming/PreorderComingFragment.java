package com.minilook.minilook.ui.preorder.view.coming;

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
import com.minilook.minilook.ui.preorder.view.coming.adapter.PreorderComingAdapter;
import com.minilook.minilook.ui.preorder.view.coming.di.PreorderComingArguments;

public class PreorderComingFragment extends BaseFragment implements PreorderComingPresenter.View {

    public static PreorderComingFragment newInstance() {
        return new PreorderComingFragment();
    }

    @BindView(R.id.rcv_preorder_coming) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) ConstraintLayout emptyPanel;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private PreorderComingPresenter presenter;
    private PreorderComingAdapter adapter = new PreorderComingAdapter();
    private BaseAdapterDataView<PreorderDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_preorder_coming;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderComingPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderComingArguments provideArguments() {
        return PreorderComingArguments.builder()
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
