package com.minilook.minilook.ui.shoppingbag;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.order.OrderActivity;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagAdapter;
import com.minilook.minilook.ui.shoppingbag.di.ShoppingBagArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.List;

public class ShoppingBagActivity extends BaseActivity implements ShoppingBagPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingBagActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.img_checkbox) ImageView checkImageView;
    @BindView(R.id.txt_selected_count) TextView checkCountTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.txt_total_count) TextView countTextView;
    @BindView(R.id.txt_total_product_price) TextView totalProductPriceTextView;
    @BindView(R.id.txt_total_shipping_price) TextView totalShippingPriceTextView;
    @BindView(R.id.txt_order) TextView orderTextView;

    @BindDimen(R.dimen.dp_8) int dp_8;

    @BindString(R.string.shoppingbag_order) String format_total_price;
    @BindString(R.string.shoppingbag_selected_count) String format_check_count;

    @BindDrawable(R.drawable.ic_checkbox2_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox2_on) Drawable img_check_on;

    private ShoppingBagPresenter presenter;
    private ShoppingBagAdapter adapter = new ShoppingBagAdapter();
    private BaseAdapterDataView<OrderBrandDataModel> adapterView = adapter;

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

    @Override public void setupCheckCount(int total, int count) {
        checkCountTextView.setText(String.format(format_check_count, count, total));
    }

    @Override public void setupTotalCount(int count) {
        countTextView.setText(String.valueOf(count));
    }

    @Override public void setupTotalProductPrice(int price) {
        totalProductPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setupTotalShippingPrice(int price) {
        totalShippingPriceTextView.setText(StringUtil.toDigit(price));
    }

    @Override public void setupTotalPrice(int price) {
        orderTextView.setText(String.format(format_total_price, StringUtil.toDigit(price)));
    }

    @Override public void checkImageView() {
        checkImageView.setImageDrawable(img_check_on);
    }

    @Override public void uncheckImageView() {
        checkImageView.setImageDrawable(img_check_off);
    }

    @Override public void navigateToOrder(List<OrderBrandDataModel> items) {
        App.getInstance().setOrderItems(items);
        OrderActivity.start(this);
    }

    @Override public void showTrialVersionDialog() {
        DialogManager.showTrialVersionDialog(this);
    }

    @OnClick(R.id.img_checkbox)
    void onCheckClick() {
        presenter.onCheckClick();
    }

    @OnClick(R.id.txt_delete)
    void onDeleteClick() {
        presenter.onDeleteClick();
    }

    @OnClick(R.id.txt_order)
    void onOrderClick() {
        presenter.onOrderClick();
    }
}
