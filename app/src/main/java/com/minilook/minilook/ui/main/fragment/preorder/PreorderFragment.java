package com.minilook.minilook.ui.main.fragment.preorder;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.preorder.di.PreorderArguments;

public class PreorderFragment extends BaseFragment implements PreorderPresenter.View {

    public static PreorderFragment newInstance() {
        return new PreorderFragment();
    }

    private PreorderPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_preorder;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderArguments provideArguments() {
        return PreorderArguments.builder()
            .view(this)
            .build();
    }
}
