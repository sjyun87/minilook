package com.minilook.minilook.ui.shipping;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.shipping.di.ShippingArguments;

public class ShippingActivity extends BaseActivity implements ShippingPresenter.View {

    private ShippingPresenter presenter;

    @BindView(R.id.rcv_shipping) RecyclerView recyclerView;

    @Override protected int getLayoutID() {
        return R.layout.activity_shipping;
    }

    @Override protected void createPresenter() {
        presenter = new ShippingPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShippingArguments provideArguments() {
        return ShippingArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
