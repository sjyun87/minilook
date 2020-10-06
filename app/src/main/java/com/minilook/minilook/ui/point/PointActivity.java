package com.minilook.minilook.ui.point;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.member.PointDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.point.adapter.PointHistoryAdapter;
import com.minilook.minilook.ui.point.di.PointArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;
import com.minilook.minilook.util.StringUtil;

public class PointActivity extends BaseActivity implements PointPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PointActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_point) TextView pointTextView;
    @BindView(R.id.rcv_point_history) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindString(R.string.point_unit) String format_point;

    private PointPresenter presenter;
    private PointHistoryAdapter adapter = new PointHistoryAdapter();
    private BaseAdapterDataView<PointDataModel> adapterView = adapter;

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
            .adapter(adapter)
            .build();
    }

    @Override public void setupPoint(int point) {
        pointTextView.setText(String.format(format_point, StringUtil.toDigit(point)));
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void emptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void navigateToWebView(String url) {
        WebViewActivity.start(this, url);
    }

    @OnClick(R.id.layout_point_info_panel)
    void onPointInfoClick() {
        presenter.onPointInfoClick();
    }
}
