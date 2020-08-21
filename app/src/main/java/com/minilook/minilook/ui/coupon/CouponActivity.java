package com.minilook.minilook.ui.coupon;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.coupon.di.CouponArguments;

public class CouponActivity extends BaseActivity implements CouponPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_coupon) RecyclerView recyclerView;

    private CouponPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_coupon;
    }

    @Override protected void createPresenter() {
        presenter = new CouponPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private CouponArguments provideArguments() {
        return CouponArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
