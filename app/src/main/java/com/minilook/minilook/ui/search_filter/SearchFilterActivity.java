package com.minilook.minilook.ui.search_filter;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.data.room.recent_keyword.RecentKeywordDB;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.search_filter.adapter.SearchFilterArguments;

public class SearchFilterActivity extends BaseActivity implements SearchFilterPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchFilterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private SearchFilterPresenter presenter;
    private RecentKeywordDB recentKeywordDB;

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
            .build();
    }
}
