package com.minilook.minilook.ui.order;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.order.di.OrderArguments;
import java.util.List;

public class OrderActivity extends BaseActivity implements OrderPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.layout_shipping_add_panel) ConstraintLayout shippingAddPanel;

    private OrderPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order;
    }

    @Override protected void createPresenter() {
        presenter = new OrderPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderArguments provideArguments() {
        return OrderArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
    }
}
