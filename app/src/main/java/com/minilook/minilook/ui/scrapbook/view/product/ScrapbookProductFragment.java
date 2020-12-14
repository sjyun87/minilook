package com.minilook.minilook.ui.scrapbook.view.product;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.FragmentScrapbookProductBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.scrapbook.view.product.adapter.ScrapbookProductAdapter;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;

public class ScrapbookProductFragment extends BaseFragment implements ScrapbookProductPresenter.View {

    public static ScrapbookProductFragment newInstance() {
        return new ScrapbookProductFragment();
    }

    private FragmentScrapbookProductBinding binding;
    private ScrapbookProductPresenter presenter;

    private final ScrapbookProductAdapter adapter = new ScrapbookProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = FragmentScrapbookProductBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ScrapbookProductPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ScrapbookProductArguments provideArguments() {
        return ScrapbookProductArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void onProductScrap(ProductDataModel data) {
        presenter.onProductScrap(data);
    }

    @Override public void setupClickAction() {
        binding.txtEmpty.setOnClickListener(view -> presenter.onEmptyClick());
    }

    @Override public void setupRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rcvProduct.setAdapter(adapter);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvProduct.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        binding.rcvProduct.addOnScrollListener(scrollListener);
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
        binding.rcvProduct.setAdapter(null);
    }
}
