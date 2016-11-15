package com.sna.xunwang.startnewandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sna.xunwang.startnewandroid.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;

public class CommWebviewActivity extends BaseActivity {
    public static final String COMMON_WEB_URL = "common_web_url";
    public static final String COMMON_WEB_TITLE = "common_web_title";

    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.common_webview)
    WebView commonWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comm_webview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String url = intent.getStringExtra(COMMON_WEB_URL);

//        setActionBarTitle(intent.getStringExtra(COMMON_WEB_TITLE));

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progressBar);

        commonWebView = (WebView) findViewById(R.id.common_webview);
        initSetting();

        MyWebChromeClient myWebChromeClient = new MyWebChromeClient();
        commonWebView.setWebChromeClient(myWebChromeClient);
        commonWebView.setWebViewClient(new MyWebViewClient());
        commonWebView.loadUrl(url);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {

    }

    private void initSetting() {
        WebSettings settings = commonWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        if (commonWebView.canGoBack()) {
            commonWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    class MyWebChromeClient extends WebChromeClient {
    }

}
