package com.minilook.minilook.ui.brand;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.databinding.ActivityBrandBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand.adapter.BrandAdapter;
import com.minilook.minilook.ui.brand.adapter.BrandStyleAdapter;
import com.minilook.minilook.ui.brand.di.BrandArguments;

public class BrandActivity extends BaseActivity implements BrandPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, BrandActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @StringRes int str_format_selected_count = R.string.brand_selected_count;

    @DimenRes int dp_2 = R.dimen.dp_2;
    @DimenRes int dp_4 = R.dimen.dp_4;

    private ActivityBrandBinding binding;
    private BrandPresenter presenter;

    private final BrandAdapter brandAdapter = new BrandAdapter();
    private final BaseAdapterDataView<BrandDataModel> brandAdapterView = brandAdapter;
    private final BrandStyleAdapter styleAdapter = new BrandStyleAdapter();
    private final BaseAdapterDataView<CodeDataModel> styleAdapterView = styleAdapter;

    @Override protected View getBindingView() {
        binding = ActivityBrandBinding.inflate(getLayoutInflater());
        return binding.getRoot();
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

    @Override public void onBrandScrap(BrandDataModel data) {
        presenter.onBrandScrap(data);
    }

    @Override public void setupClickAction() {
        binding.txtReset.setOnClickListener(view -> presenter.onResetClick());
        binding.txtEmpty.setOnClickListener(view -> presenter.onResetClick());
    }

    @Override public void setupStyleRecyclerView() {
        binding.rcvStyle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        styleAdapter.setOnStyleClickListener(presenter::onStyleClick);
        binding.rcvStyle.setAdapter(styleAdapter);
        DividerDecoration.builder(this)
            .size(getResources().getDimensionPixelSize(dp_4))
            .asSpace()
            .build()
            .addTo(binding.rcvStyle);
    }

    @Override public void styleRefresh() {
        styleAdapterView.refresh();
    }

    @Override public void setupBrandRecyclerView() {
        binding.rcvBrand.setHasFixedSize(true);
        binding.rcvBrand.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvBrand.setAdapter(brandAdapter);
        DividerDecoration.builder(this)
            .size(getResources().getDimensionPixelSize(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvBrand);
    }

    @Override public void brandRefresh() {
        brandAdapterView.refresh();
    }

    @Override public void setupSelectedStyleCount(int count, int total) {
        binding.txtSelectedCount.setText(String.format(getString(str_format_selected_count), count, total));
    }

    @Override public void showEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.GONE);
    }
}
