package com.minilook.minilook.ui.point;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.point.di.PointArguments;

public class PointActivity extends BaseActivity implements PointPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PointActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_point_history) RecyclerView recyclerView;

    private PointPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_point;
    }

    @Override protected void createPresenter() {
        presenter = new PointPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PointArguments provideArguments() {
        return PointArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
