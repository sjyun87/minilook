package com.minilook.minilook.ui.shipping;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.shipping.di.ShippingArguments;
import com.minilook.minilook.ui.shipping_add.ShippingAddActivity;

public class ShippingActivity extends BaseActivity implements ShippingPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShippingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

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


    @OnClick(R.id.txt_add)
    void onAddClick() {
        ShippingAddActivity.start(this);
    }
}
