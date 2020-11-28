package com.minilook.minilook.ui.lookbook.view.detail;

import android.view.View;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.databinding.FragmentLookbookDetailBinding;
import com.minilook.minilook.databinding.FragmentLookbookPreviewBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base._BaseFragment;
import com.minilook.minilook.ui.lookbook.view.detail.adapter.LookBookProductAdapter;
import com.minilook.minilook.ui.lookbook.view.detail.adapter.LookBookStyleAdapter;
import com.minilook.minilook.ui.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;

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
        binding.rcvStyle.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(binding.rcvStyle, false);
    }

    @Override public void styleRefresh() {
        styleAdapterDataView.refresh();
    }

    @Override public void setupProductRecyclerView() {
        binding.rcvProduct.setHasFixedSize(true);
        binding.rcvProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvProduct.setAdapter(productAdapter);
        ViewCompat.setNestedScrollingEnabled(binding.rcvProduct, false);
    }

    @Override public void productRefresh() {
        productAdapterDataView.refresh();
    }

    @Override public void setupLabel(String text) {
        binding.txtLabel.setText(text);
    }

    @Override public void setupTitle(String text) {
        binding.txtTitle.setText(text);
    }

    @Override public void setupTag(String text) {
        binding.txtTag.setText(text);
    }

    @Override public void setupDesc(String text) {
        binding.txtDesc.setText(text);
    }

    @Override public void setupSimpleInfo(String text) {
        binding.txtProductInfo.setText(text);
    }

    @Override public void scrollToTop() {
        binding.nsvContents.smoothScrollTo(0, 0);
        binding.rcvStyle.scrollToPosition(0);
    }
}
