package com.minilook.minilook.ui.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.detail.di.DetailArguments;

import butterknife.BindView;

public class DetailActivity extends BaseActivity implements DetailPresenter.View {

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("web_url", url);
        context.startActivity(intent);
    }

    @BindView(R.id.webview) WebView webView;

    private DetailPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_detail;
    }

    @Override protected void createPresenter() {
        presenter = new DetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private DetailArguments provideArguments() {
        return DetailArguments.builder()
            .view(this)
            .webUrl(getIntent().getStringExtra("web_url"))
            .build();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override public void loadUrl(String url) {
        webView.loadUrl(url);
    }
}
