package com.minilook.minilook.ui.coupon;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindString;
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
import com.minilook.minilook.ui.dialog.manager.DialogManager;
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

    @BindString(R.string.dialog_regist_coupon_invalid) String str_invalid;
    @BindString(R.string.dialog_regist_coupon_duplicate) String str_duplicate;
    @BindString(R.string.dialog_regist_coupon_complete) String str_complete;

    @BindDimen(R.dimen.dp_8) int dp_8;

    private CouponPresenter presenter;
    private CouponAdapter adapter = new CouponAdapter();
    private BaseAdapterDataView<CouponDataModel> adapterView = adapter;
    private IntentIntegrator integrator;

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

    @Override public void showQRCodeScanner() {
        integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override public void showRegistCouponDialog() {
        DialogManager.showRegistCouponDialog(this);
    }

    @Override public void showDuplicateMessage() {
        Toast.makeText(this, str_duplicate, Toast.LENGTH_SHORT).show();
    }

    @Override public void showInvalidMessage() {
        Toast.makeText(this, str_invalid, Toast.LENGTH_SHORT).show();
    }

    @Override public void showRegistCompleteMessage() {
        Toast.makeText(this, str_complete, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_coupon_info_panel)
    void onCouponInfoClick() {
        presenter.onCouponInfoClick();
    }

    @OnClick(R.id.layout_coupon_code)
    void onRegistCodeClick() {
        presenter.onRegistCouponCodeClick();
    }

    @OnClick(R.id.layout_coupon_qr)
    void onRegistQRCodeClick() {
        presenter.onRegistQRCodeClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                presenter.onQRScan(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
