package com.minilook.minilook.ui.search_filter;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.slider.Slider;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_filter.adapter.FilterGenderAdapter;
import com.minilook.minilook.ui.search_filter.di.SearchFilterArguments;

public class SearchFilterActivity extends BaseActivity implements SearchFilterPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchFilterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_gender) RecyclerView genderRecyclerView;
    @BindView(R.id.slider_gender) Slider ageSlider;
    @BindView(R.id.txt_age) TextView ageTextView;

    @BindDimen(R.dimen.dp_5) int dp_5;
    @BindString(R.string.search_filter_age_all) String format_all;
    @BindString(R.string.search_filter_age_month) String format_month;
    @BindString(R.string.search_filter_age_year) String format_year;

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

    @Override public void setupGenderRecyclerView() {
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

    @Override public void setupAgeSlider() {
        ageSlider.addOnChangeListener((slider, value, fromUser) -> presenter.onAgeChanged(value));
    }

    @Override public void setupAge(int age, boolean isBaby) {
        if (isBaby) {
            if (age == 0) {
                ageTextView.setText(format_all);
            } else {
                ageTextView.setText(String.format(format_month, age));
            }
        } else {
            ageTextView.setText(String.format(format_year, age));
        }
    }
}
