package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter.LookBookProductAdapter;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter.LookBookStyleAdapter;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.di.LookBookDetailArguments;

public class LookBookDetailFragment extends BaseFragment implements LookBookDetailPresenter.View {

    public static LookBookDetailFragment newInstance() {
        return new LookBookDetailFragment();
    }

    @BindView(R.id.txt_label) TextView labelTextView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.rcv_style) RecyclerView styleRecyclerView;
    @BindView(R.id.txt_product_info) TextView productInfoTextView;
    @BindView(R.id.rcv_product) RecyclerView productRecyclerView;

    private LookBookDetailPresenter presenter;
    private LookBookStyleAdapter styleAdapter = new LookBookStyleAdapter();
    private BaseAdapterDataView<String> styleAdapterDataView = styleAdapter;
    private LookBookProductAdapter productAdapter = new LookBookProductAdapter();
    private BaseAdapterDataView<ProductDataModel> productAdapterDataView = productAdapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_lookbook_detail;
    }

    @Override protected void createPresenter() {
        presenter = new LookBookDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private LookBookDetailArguments provideArguments() {
        return LookBookDetailArguments.builder()
            .view(this)
            .styleAdapter(styleAdapter)
            .productAdapter(productAdapter)
            .build();
    }

    @Override public void setupStyleRecyclerView() {
        styleRecyclerView.setAdapter(styleAdapter);
    }

    @Override public void setupProductRecyclerView() {
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override public void setupLabel(String text) {
        labelTextView.setText(text);
    }

    @Override public void setupTitle(String text) {
        titleTextView.setText(text);
    }

    @Override public void setupTag(String text) {
        tagTextView.setText(text);
    }

    @Override public void setupDescription(String text) {
        descTextView.setText(text);
    }

    @Override public void setupProductInfo(String text) {
        productInfoTextView.setText(text);
    }

    @Override public void styleRefresh() {
        styleAdapterDataView.refresh();
    }

    @Override public void productRefresh() {
        productAdapterDataView.refresh();
    }

    @OnClick(R.id.img_titlebar_back)
    void onBackClick() {

    }
}
