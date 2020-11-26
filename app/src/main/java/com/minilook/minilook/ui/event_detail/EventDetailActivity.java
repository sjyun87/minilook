package com.minilook.minilook.ui.event_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.event_detail.adapter.EventAdapter;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;
import com.minilook.minilook.data.firebase.DynamicLinkManager;

public class EventDetailActivity extends _BaseActivity implements EventDetailPresenter.View {

    public static void start(Context context, int eventNo) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("eventNo", eventNo);
        context.startActivity(intent);
    }

    @BindView(R.id.img_event) ImageView eventImageView;
    @BindView(R.id.rcv_event) RecyclerView eventRecyclerView;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_image_wide) Drawable img_placeholder_wide;

    @BindString(R.string.dialog_error_title) String str_error_msg;

    @BindDimen(R.dimen.dp_2) int dp_2;

    private EventDetailPresenter presenter;
    private EventAdapter adapter = new EventAdapter();
    private BaseAdapterDataView<EventDataModel> adapterView = adapter;

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
            .eventNo(getIntent().getIntExtra("eventNo", -1))
            .adapter(adapter)
            .dynamicLinkManager(new DynamicLinkManager(this))
            .build();
    }

    @Override public void setupRecyclerView() {
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        eventRecyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(eventRecyclerView);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(eventRecyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(4)
                .build();
        eventRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void sendLink(String shareLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareLink);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    @Override public void showErrorMessage() {
        Toast.makeText(this, str_error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override public void setupEventImage(String url) {
        Glide.with(this)
            .load(url)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(eventImageView);
    }

    @OnClick(R.id.img_titlebar_share)
    void onShareClick() {
        presenter.onShareClick();
    }
}
