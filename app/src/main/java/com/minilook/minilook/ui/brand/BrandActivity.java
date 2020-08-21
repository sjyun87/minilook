package com.minilook.minilook.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.brand.adapter.BrandStyleAdapter;
import com.minilook.minilook.ui.brand.di.BrandArguments;

public class BrandActivity extends BaseActivity implements BrandPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, BrandActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_selected_count) TextView selectedCountTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.rcv_brand) RecyclerView brandRecyclerView;

    @BindString(R.string.brand_selected_count) String format_selected_count;

    private BrandPresenter presenter;
    private BrandStyleAdapter styleAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_brand;
    }

    @Override protected void createPresenter() {
        presenter = new BrandPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private BrandArguments provideArguments() {
        return BrandArguments.builder()
            .view(this)
            .build();
    }

    @OnClick(R.id.txt_reset)
    void resetClick() {
        presenter.resetClick();
    }

    @Override public void setupStyleRecyclerView() {
        styleRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        styleRecyclerView.setAdapter(styleAdapter);
    }

    @Override public void setupBrandRecyclerView() {

    }
}
