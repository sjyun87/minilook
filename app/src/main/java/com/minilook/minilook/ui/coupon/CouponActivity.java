package com.minilook.minilook.ui.coupon;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.member.CouponDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.coupon.adapter.CouponAdapter;
import com.minilook.minilook.ui.coupon.di.CouponArguments;
import com.minilook.minilook.ui.webview.WebViewActivity;

public class CouponActivity extends _BaseActivity implements CouponPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_coupon) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindDimen(R.dimen.dp_8) int dp_8;

    private CouponPresenter presenter;
    private CouponAdapter adapter = new CouponAdapter();
    private BaseAdapterDataView<CouponDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_coupon;
    }

    @Override protected void createPresenter() {
        presenter = new CouponPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private CouponArguments provideArguments() {
        return CouponArguments.builder()
            .view(this)
            .adapter(adapter)
            .integrator(new IntentIntegrator(this))
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_8)
            .asSpace()
            .build()
            .addTo(recyclerView);
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

    @OnClick(R.id.layout_coupon_info_panel)
    void onCouponInfoClick() {
        presenter.onCouponInfoClick();
    }

    @OnClick(R.id.txt_regist)
    void onRegistClick() {
        presenter.onCouponRegistClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
