package com.minilook.minilook.ui.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.webview.di.WebViewArguments;

public class WebViewActivity extends BaseActivity implements WebViewPresenter.View {

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @BindView(R.id.webview) WebView webView;

    private WebViewPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_webview;
    }

    @Override protected void createPresenter() {
        presenter = new WebViewPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private WebViewArguments provideArguments() {
        return WebViewArguments.builder()
            .view(this)
            .url(getIntent().getStringExtra("url"))
            .build();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(customWebClient);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    WebViewClient customWebClient = new WebViewClient(){
        @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    };
}
