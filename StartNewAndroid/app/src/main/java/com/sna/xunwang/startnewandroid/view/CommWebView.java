package com.sna.xunwang.startnewandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.XLog;

/**
 * Created by xunwang on 16/11/10.
 */

public class CommWebView extends FrameLayout {
//    @BindView(R.id.comm_webview)
    WebView webView;

    public CommWebView(Context context) {
        this(context, null);
    }

    public CommWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_comm_webview, null);
//        ButterKnife.bind(this, v);
        webView = (WebView) v.findViewById(R.id.comm_webview);
//        setWebSetting(webView.getSettings());
        webView.setWebChromeClient(new BaseWebChromeClient());
        webView.setWebViewClient(new BaseWebViewClient());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    protected void setWebSetting(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setAppCacheEnabled(true);
//        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        setting.setAppCacheEnabled(false);
//        setting.setBuiltInZoomControls(false);
        setting.setSupportZoom(false);
//        setting.setDomStorageEnabled(true);
//        setting.setUseWideViewPort(true);
//        setting.setGeolocationEnabled(true);
//        setting.setAllowFileAccess(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            setting.setAllowFileAccessFromFileURLs(false);
//            setting.setAllowUniversalAccessFromFileURLs(false);
//        }
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public class BaseWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    public class BaseWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            XLog.d(Constants.TAG, "onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            XLog.d(Constants.TAG, "onPageFinished");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            XLog.d(Constants.TAG, "onReceivedError");

        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, final SslError error) {
            handler.proceed();//接受证书
        }

    }
}
