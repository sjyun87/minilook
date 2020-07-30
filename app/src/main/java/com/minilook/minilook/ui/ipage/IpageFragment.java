package com.minilook.minilook.ui.ipage;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.ipage.di.IpageArguments;

public class IpageFragment extends BaseFragment implements IpagePresenter.View {

    public static IpageFragment newInstance() {
        return new IpageFragment();
    }

    private IpagePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_ipage;
    }

    @Override protected void createPresenter() {
        presenter = new IpagePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private IpageArguments provideArguments() {
        return IpageArguments.builder()
            .view(this)
            .build();
    }
}
