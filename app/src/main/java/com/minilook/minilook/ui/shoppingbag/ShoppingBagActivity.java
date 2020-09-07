package com.minilook.minilook.ui.shoppingbag;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.ShoppingBagDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagAdapter;
import com.minilook.minilook.ui.shoppingbag.di.ShoppingBagArguments;

public class ShoppingBagActivity extends BaseActivity implements ShoppingBagPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingBagActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_8) int dp_8;

    private ShoppingBagPresenter presenter;
    private ShoppingBagAdapter adapter = new ShoppingBagAdapter();
    private BaseAdapterDataView<ShoppingBagDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_shopping_bag;
    }

    @Override protected void createPresenter() {
        presenter = new ShoppingBagPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShoppingBagArguments provideArguments() {
        return ShoppingBagArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_8)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }
}
