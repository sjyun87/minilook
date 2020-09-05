package com.minilook.minilook.ui.webview.di;

import com.minilook.minilook.ui.webview.WebViewPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class WebViewArguments {
    private final WebViewPresenter.View view;
    private final String url;
}