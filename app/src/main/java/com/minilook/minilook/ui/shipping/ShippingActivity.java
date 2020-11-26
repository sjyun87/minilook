package com.minilook.minilook.ui.shipping;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.shipping.adapter.ShippingAdapter;
import com.minilook.minilook.ui.shipping.di.ShippingArguments;
import com.minilook.minilook.ui.shipping_update.ShippingUpdateActivity;

public class ShippingActivity extends _BaseActivity implements ShippingPresenter.View {

    public static void start(Context context, String route) {
        Intent intent = new Intent(context, ShippingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("route", route);
        context.startActivity(intent);
    }

    private ShippingPresenter presenter;
    private ShippingAdapter adapter = new ShippingAdapter();
    private BaseAdapterDataView<ShippingDataModel> adapterView = adapter;

    @BindView(R.id.rcv_shipping) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindDimen(R.dimen.dp_6) int dp_6;

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
            .route(getIntent().getStringExtra("route"))
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_6)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        emptyPanel.setVisibility(View.GONE);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void showDefaultShippingDialog() {
        DialogManager.showDefaultShippingDialog(this, presenter::onDefaultShippingDialogOkClick);
    }

    @Override public void navigateToShippingAdd() {
        ShippingUpdateActivity.start(this);
    }

    @Override public void navigateToShippingEdit(ShippingDataModel data) {
        ShippingUpdateActivity.start(this, data);
    }

    @OnClick({R.id.txt_add, R.id.txt_empty})
    void onAddClick() {
        presenter.onAddClick();
    }
}
