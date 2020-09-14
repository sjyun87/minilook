package com.minilook.minilook.ui.order_complete;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.order_complete.di.OrderCompleteArguments;
import com.minilook.minilook.ui.order_history.OrderHistoryActivity;

public class OrderCompleteActivity extends BaseActivity implements OrderCompletePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderCompleteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private OrderCompletePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_complete;
    }

    @Override protected void createPresenter() {
        presenter = new OrderCompletePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderCompleteArguments provideArguments() {
        return OrderCompleteArguments.builder()
            .view(this)
            .build();
    }

    @OnClick(R.id.txt_order_history)
    void onOrderHistoryClick() {
        presenter.onOrderHistoryClick();
    }

    @OnClick(R.id.txt_shopping)
    void onShoppingClick() {
        presenter.onShoppingClick();
    }

    @Override public void navigateToOrderHistory() {
        OrderHistoryActivity.start(this);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this, BottomBar.POSITION_MARKET);
    }

    @Override public void onBackPressed() {
    }
}
