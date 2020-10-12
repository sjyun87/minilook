package com.minilook.minilook.ui.preorder.view.close;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.preorder.adapter.PreorderAdapter;
import com.minilook.minilook.ui.preorder.view.close.di.PreorderCloseArguments;

public class PreorderCloseFragment extends BaseFragment implements PreorderClosePresenter.View {

    public static PreorderCloseFragment newInstance() {
        return new PreorderCloseFragment();
    }

    //@BindView(R.id.rcv_preorder_open) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private PreorderClosePresenter presenter;
    private PreorderAdapter adapter = new PreorderAdapter();
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
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(adapter);
        //DividerDecoration.builder(requireContext())
        //    .size(dp_4)
        //    .asSpace()
        //    .build()
        //    .addTo(recyclerView);
    }

    @Override public void refresh() {
        //adapterView.refresh();
    }
}
