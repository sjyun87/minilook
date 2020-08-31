package com.minilook.minilook.ui.search_address;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.search_address.di.SearchAddressArguments;
import timber.log.Timber;

public class SearchAddressActivity extends BaseActivity implements SearchAddressPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchAddressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.webView) WebView webView;

    private SearchAddressPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_search_address;
    }

    @Override protected void createPresenter() {
        presenter = new SearchAddressPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchAddressArguments provideArguments() {
        return SearchAddressArguments.builder()
            .view(this)
            .build();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override public void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.loadUrl("http://lookbook.minilook.co.kr/m/bbs/register_form.php");
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            // Dialog Create Code
            WebView newWebView = new WebView(SearchAddressActivity.this);
            WebSettings webSettings = newWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            final Dialog dialog = new Dialog(SearchAddressActivity.this);
            dialog.setContentView(newWebView);

            ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
            dialog.show();
            newWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onCloseWindow(WebView window) {
                    dialog.dismiss();
                }
            });

            // WebView Popup에서 내용이 안보이고 빈 화면만 보여 아래 코드 추가
            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return false;
                }
            });

            ((WebView.WebViewTransport)resultMsg.obj).setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;

        }

    }
}
