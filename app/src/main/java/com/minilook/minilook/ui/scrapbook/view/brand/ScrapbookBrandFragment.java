package com.minilook.minilook.ui.scrapbook.view.brand;

import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.databinding.FragmentScrapbookBrandBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.scrapbook.view.brand.adapter.ScrapbookBrandAdapter;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;

public class ScrapbookBrandFragment extends BaseFragment implements ScrapbookBrandPresenter.View {

    public static ScrapbookBrandFragment newInstance() {
        return new ScrapbookBrandFragment();
    }

    @DimenRes int dp_6 = R.dimen.dp_6;

    @ColorRes int color_FFF5F5F5 = R.color.color_FFF5F5F5;

    private FragmentScrapbookBrandBinding binding;
    private ScrapbookBrandPresenter presenter;

    private final ScrapbookBrandAdapter adapter = new ScrapbookBrandAdapter();
    private final BaseAdapterDataView<BrandDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = FragmentScrapbookBrandBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ScrapbookBrandPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ScrapbookBrandArguments provideArguments() {
        return ScrapbookBrandArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void onBrandScrap(BrandDataModel data) {
        presenter.onBrandScrap(data);
    }

    @Override public void setupClickAction() {
        binding.txtEmpty.setOnClickListener(view -> presenter.onEmptyClick());
    }

    @Override public void setupRecyclerView() {
        binding.rcvBrand.setHasFixedSize(true);
        binding.rcvBrand.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvBrand.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(resources.getDimen(dp_6))
            .color(resources.getColor(color_FFF5F5F5))
            .build()
            .addTo(binding.rcvBrand);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvBrand.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(5)
                .build();
        binding.rcvBrand.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int position) {
        adapterView.refresh(position);
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(requireActivity());
    }

    @Override public void clear() {
        binding.rcvBrand.setAdapter(null);
    }
}
