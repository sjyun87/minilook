package com.minilook.minilook.ui.search_zip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.search_zip.di.SearchZipArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class SearchZipActivity extends _BaseActivity implements SearchZipPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchZipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.root) FrameLayout rootView;
    @BindView(R.id.webview) WebView webView;

    private SearchZipPresenter presenter;
    private WebViewClient webViewClient = new WebViewClient();
    private CustomWebChromeClient webChromeClient = new CustomWebChromeClient();

    @Override protected int getLayoutID() {
        return R.layout.activity_search_zip;
    }

    @Override protected void createPresenter() {
        presenter = new SearchZipPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchZipArguments provideArguments() {
        return SearchZipArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupWebView() {
        setupWebViewInit(webView);
        webView.addJavascriptInterface(new AndroidBridgeInterface(), "MinilookApp");
    }

    @Override public void loadURL(String url) {
        webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebViewInit(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView childWebView = new WebView(view.getContext());
            setupWebViewInit(childWebView);
            childWebView.setLayoutParams(view.getLayoutParams());
            rootView.addView(childWebView);

            ((WebView.WebViewTransport) resultMsg.obj).setWebView(childWebView);
            resultMsg.sendToTarget();
            return true;
        }

        @Override public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            rootView.removeView(window);
        }
    }

    private class AndroidBridgeInterface {
        @JavascriptInterface
        public void addressResult(String json) {
            Timber.e(json);
            RxBus.send(new RxEventSearchAddressComplete(json));
            finish();
        }
    }

    @AllArgsConstructor @Getter public final static class RxEventSearchAddressComplete {
        private String json;
    }
}
