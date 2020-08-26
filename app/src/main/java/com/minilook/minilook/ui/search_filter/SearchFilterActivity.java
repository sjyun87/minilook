package com.minilook.minilook.ui.search_filter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_filter.adapter.FilterGenderAdapter;
import com.minilook.minilook.ui.search_filter.di.SearchFilterArguments;
import com.minilook.minilook.ui.search_filter.viewholder.FilterGenderVH;

public class SearchFilterActivity extends BaseActivity implements SearchFilterPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchFilterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_gender) RecyclerView genderRecyclerView;


    @BindDimen(R.dimen.dp_5) int dp_5;

    private SearchFilterPresenter presenter;
    private FilterGenderAdapter genderAdapter = new FilterGenderAdapter();
    private BaseAdapterDataView<GenderDataModel> genderAdapterView = genderAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_search_filter;
    }

    @Override protected void createPresenter() {
        presenter = new SearchFilterPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchFilterArguments provideArguments() {
        return SearchFilterArguments.builder()
            .view(this)
            .genderAdapter(genderAdapter)
            .build();
    }

    @Override public void genderRecyclerView() {
        genderRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        genderAdapter.setListener(presenter::onGenderSelected);
        genderRecyclerView.setAdapter(genderAdapter);
        DividerDecoration.builder(this)
            .size(dp_5)
            .asSpace()
            .build()
            .addTo(genderRecyclerView);
    }

    @Override public void genderRefresh() {
        genderAdapterView.refresh();
    }
}
