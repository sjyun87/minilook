package com.minilook.minilook.ui.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.payment.di.PaymentArguments;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;

public class PaymentActivity extends BaseActivity implements PaymentPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.webView) WebView webView;

    private PaymentPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_payment;
    }

    @Override protected void createPresenter() {
        presenter = new PaymentPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PaymentArguments provideArguments() {
        return PaymentArguments.builder()
            .view(this)
            .build();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        String postData = "P_INI_PAYME=CARD" + "&"
            + "P_MID=INIpayTest"  + "&"
            + "P_OID=minilook1234" + "&"
            + "P_AMT=10000" + "&"
            + "P_UNAME=미니룩" + "&"
            + "P_GOODS=결제 테스트";

            //webView.postUrl("https://mobile.inicis.com/smart/payment/", Encoding);
    }
}
