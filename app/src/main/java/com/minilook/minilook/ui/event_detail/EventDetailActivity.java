package com.minilook.minilook.ui.event_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.databinding.ActivityEventDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.event_detail.adapter.EventDetailAdapter;
import com.minilook.minilook.ui.event_detail.di.EventDetailArguments;

public class EventDetailActivity extends BaseActivity implements EventDetailPresenter.View {

    public static void start(Context context, int eventNo) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("eventNo", eventNo);
        context.startActivity(intent);
    }

    @DrawableRes int ph_square = R.drawable.ph_square;

    @DimenRes int dp_2 = R.dimen.dp_2;

    private ActivityEventDetailBinding binding;
    private EventDetailPresenter presenter;

    private final EventDetailAdapter adapter = new EventDetailAdapter();
    private final BaseAdapterDataView<EventDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityEventDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
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
            .build();
    }

    @Override public void setupClickAction() {
        binding.titlebar.getBinding().imgTitlebarShare.setOnClickListener(view -> presenter.onShareClick());
    }

    @Override public void setupRecyclerView() {
        binding.rcvEvent.setHasFixedSize(true);
        binding.rcvEvent.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvEvent.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_2))
            .asSpace()
            .build()
            .addTo(binding.rcvEvent);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvEvent.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(4)
                .build();
        binding.rcvEvent.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void setEventImage(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgEvent);
    }

    @Override public void hideOtherEvents() {
        binding.layoutOtherPanel.setVisibility(View.GONE);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void clear() {
        binding.rcvEvent.setLayoutManager(null);
        binding.rcvEvent.setAdapter(null);
    }
}
