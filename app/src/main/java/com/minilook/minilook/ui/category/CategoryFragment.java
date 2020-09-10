package com.minilook.minilook.ui.category;

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.category.adapter.CategoryAdapter;
import com.minilook.minilook.ui.category.di.CategoryArguments;

import butterknife.BindDimen;
import butterknife.BindView;

public class CategoryFragment extends BaseFragment implements CategoryPresenter.View {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @BindView(R.id.rcv_category) RecyclerView recyclerView;

    @BindDimen(R.dimen.dp_6) int dp_6;

    private CategoryPresenter presenter;
    private CategoryAdapter adapter = new CategoryAdapter();
    private BaseAdapterDataView<CategoryDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.fragment_category;
    }

    @Override protected void createPresenter() {
        presenter = new CategoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private CategoryArguments provideArguments() {
        return CategoryArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(dp_6)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void navigateToYoutube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    @OnClick(R.id.img_banner_video)
    void onBannerVideoClick() {
        //presenter.onBannerVideoClick();
    }
}
