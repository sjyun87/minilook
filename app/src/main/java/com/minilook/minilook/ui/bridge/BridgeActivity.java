package com.minilook.minilook.ui.bridge;

import android.content.Context;
import android.content.Intent;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.bridge.di.BridgeArguments;

public class BridgeActivity extends BaseActivity implements BridgePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, BridgeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private BridgePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_bridge;
    }

    @Override protected void createPresenter() {
        presenter = new BridgePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BridgeArguments provideArguments() {
        return BridgeArguments.builder()
            .view(this)
            .build();
    }

}
