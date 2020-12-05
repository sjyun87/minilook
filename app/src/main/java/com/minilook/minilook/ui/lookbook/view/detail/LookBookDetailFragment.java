package com.minilook.minilook.ui.lookbook.view.detail;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.FragmentLookbookDetailBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.lookbook.view.detail.adapter.LookBookProductAdapter;
import com.minilook.minilook.ui.lookbook.view.detail.adapter.LookBookStyleAdapter;
import com.minilook.minilook.ui.lookbook.view.detail.di.LookBookDetailArguments;

public class LookBookDetailFragment extends BaseFragment implements LookBookDetailPresenter.View {

    public static LookBookDetailFragment newInstance() {
        return new LookBookDetailFragment();
    }

    private FragmentLookbookDetailBinding binding;
    private LookBookDetailPresenter presenter;

    private final LookBookStyleAdapter styleAdapter = new LookBookStyleAdapter();
    private final BaseAdapterDataView<String> styleAdapterDataView = styleAdapter;
    private final LookBookProductAdapter productAdapter = new LookBookProductAdapter();
    private final BaseAdapterDataView<ProductDataModel> productAdapterDataView = productAdapter;

    @Override protected View getBindingView() {
        binding = FragmentLookbookDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new LookBookDetailPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private LookBookDetailArguments provideArguments() {
        return LookBookDetailArguments.builder()
            .view(this)
            .styleAdapter(styleAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupTitleBar() {
        binding.titlebar.getBinding().imgTitlebarBack.setOnClickListener(view -> presenter.onBackClick());
    }

    @Override public void setupStyleRecyclerView() {
        binding.rcvStyle.setHasFixedSize(true);
        binding.rcvStyle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rcvStyle.setAdapter(styleAdapter);
    }

    @Override public void styleRefresh() {
        styleAdapterDataView.refresh();
    }

    @Override public void setupProductRecyclerView() {
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvProduct.setAdapter(productAdapter);
    }

    @Override public void productRefresh() {
        productAdapterDataView.refresh();
    }

    @Override public void setLabel(String text) {
        binding.txtLabel.setText(text);
    }

    @Override public void setTitle(String text) {
        binding.txtTitle.setText(text);
    }

    @Override public void setTag(String text) {
        binding.txtTag.setText(text);
    }

    @Override public void setDesc(String text) {
        binding.txtDesc.setText(text);
    }

    @Override public void setSimpleInfo(String text) {
        binding.txtProductInfo.setText(text);
    }

    @Override public void scrollToTop() {
        binding.nsvContents.smoothScrollTo(0, 0);
        binding.rcvStyle.scrollToPosition(0);
    }
}
