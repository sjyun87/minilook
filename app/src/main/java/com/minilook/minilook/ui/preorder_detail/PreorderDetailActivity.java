package com.minilook.minilook.ui.preorder_detail;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.data.room.recent_keyword.RecentKeywordDB;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.preorder_detail.adapter.PreorderDetailArguments;

public class PreorderDetailActivity extends BaseActivity implements PreorderDetailPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PreorderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private PreorderDetailPresenter presenter;
    private RecentKeywordDB recentKeywordDB;

    @Override protected int getLayoutID() {
        return R.layout.activity_search_filter;
    }

    @Override protected void createPresenter() {
        presenter = new PreorderDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderDetailArguments provideArguments() {
        return PreorderDetailArguments.builder()
            .view(this)
            .build();
    }
}
