package com.minilook.minilook.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand.adapter.BrandAdapter;
import com.minilook.minilook.ui.brand.adapter.BrandStyleAdapter;
import com.minilook.minilook.ui.brand.di.BrandArguments;

public class BrandActivity extends _BaseActivity implements BrandPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, BrandActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_selected_count) TextView selectedCountTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.rcv_brand) RecyclerView brandRecyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindString(R.string.brand_selected_count) String format_selected_count;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private BrandPresenter presenter;
    private BrandAdapter brandAdapter = new BrandAdapter();
    private BaseAdapterDataView<BrandDataModel> brandAdapterView = brandAdapter;
    private BrandStyleAdapter styleAdapter = new BrandStyleAdapter();
    private BaseAdapterDataView<CodeDataModel> styleAdapterView = styleAdapter;

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
            .styleAdapter(styleAdapter)
            .brandAdapter(brandAdapter)
            .build();
    }

    @Override public void setupStyleRecyclerView() {
        styleRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        styleAdapter.setOnStyleClickListener(presenter::onStyleClick);
        styleRecyclerView.setAdapter(styleAdapter);
    }

    @Override public void styleRefresh() {
        styleAdapterView.refresh();
    }

    @Override public void setupBrandRecyclerView() {
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        brandRecyclerView.setAdapter(brandAdapter);
        DividerDecoration.builder(this)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(styleRecyclerView);
    }

    @Override public void brandRefresh() {
        brandAdapterView.refresh();
    }

    @Override public void setupSelectedStyleCount(int count, int total) {
        selectedCountTextView.setText(String.format(format_selected_count, count, total));
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        emptyPanel.setVisibility(View.GONE);
    }

    @OnClick(R.id.txt_reset)
    void onResetClick() {
        presenter.onResetClick();
    }

    @OnClick(R.id.txt_empty)
    void onEmptyClick() {
        presenter.onResetClick();
    }
}
