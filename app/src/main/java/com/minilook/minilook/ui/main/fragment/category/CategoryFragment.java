package com.minilook.minilook.ui.main.fragment.category;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.category.CategoryItemDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.category.adapter.CategoryAdapter;
import com.minilook.minilook.ui.main.fragment.category.di.CategoryArguments;

import butterknife.BindView;

public class CategoryFragment extends BaseFragment implements CategoryPresenter.View {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @BindView(R.id.rcv_category) RecyclerView categoryRecyclerView;

    private CategoryPresenter presenter;
    private CategoryAdapter adapter = new CategoryAdapter();
    private BaseAdapterDataView<CategoryItemDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_category;
    }

    @Override protected void createPresenter() {
        presenter = new CategoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private CategoryArguments provideArguments() {
        return CategoryArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        categoryRecyclerView.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(getResources().getDimensionPixelSize(R.dimen.dp_5))
            .asSpace()
            .build()
            .addTo(categoryRecyclerView);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
