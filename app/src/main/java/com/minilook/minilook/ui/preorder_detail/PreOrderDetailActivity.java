package com.minilook.minilook.ui.preorder_detail;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.data.room.recent_keyword.RecentKeywordDB;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.preorder_detail.adapter.PreOrderDetailArguments;

public class PreOrderDetailActivity extends BaseActivity implements PreOrderDetailPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PreOrderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private PreOrderDetailPresenter presenter;
    private RecentKeywordDB recentKeywordDB;

    @Override protected int getLayoutID() {
        return R.layout.activity_search_filter;
    }

    @Override protected void createPresenter() {
        presenter = new PreOrderDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreOrderDetailArguments provideArguments() {
        return PreOrderDetailArguments.builder()
            .view(this)
            .build();
    }
}
