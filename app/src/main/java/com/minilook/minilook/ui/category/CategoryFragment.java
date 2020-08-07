package com.minilook.minilook.ui.category;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.category.di.CategoryArguments;

public class CategoryFragment extends BaseFragment implements CategoryPresenter.View {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    private CategoryPresenter presenter;

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
            .build();
    }
}
