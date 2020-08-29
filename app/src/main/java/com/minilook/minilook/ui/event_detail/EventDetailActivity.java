package com.minilook.minilook.ui.event_detail;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;

public class EventDetailActivity extends BaseActivity implements EventDetailPresenter.View {

    public static void start(Context context, int event_id) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("event_id", event_id);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_goods) RecyclerView recyclerView;

    private EventDetailPresenter presenter;
    private ProductAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_event_detail;
    }

    @Override protected void createPresenter() {
        presenter = new EventDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private EventDetailArguments provideArguments() {
        return EventDetailArguments.builder()
            .view(this)
            .event_id(getIntent().getIntExtra("event_id", -1))
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setViewType(ProductAdapter.VIEW_TYPE_WIDE);
        recyclerView.setAdapter(adapter);
    }
}
