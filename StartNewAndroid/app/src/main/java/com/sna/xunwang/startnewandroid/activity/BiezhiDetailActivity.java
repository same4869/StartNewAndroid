package com.sna.xunwang.startnewandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.XLog;

import butterknife.BindView;
import butterknife.OnClick;

public class BiezhiDetailActivity extends BaseActivity {
    private static final String EXTRA_DETAIL = "extra_detail";
    @BindView(R.id.bzd_detail_image)
    ImageView bzdDetailImage;
    @BindView(R.id.bzd_detail_title)
    TextView detailTitle;
    @BindView(R.id.bzd_detail_source)
    TextView detailSource;
//    @BindView(R.id.bzd_detail_web_view)
//    CommWebView webView;

    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_biezhi_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        webView.setWebChromeClient(new BaseWebChromeClient());
//        webView.setWebViewClient(new BaseWebViewClient());
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

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            BiezhiGoodsBean dailyBean = (BiezhiGoodsBean) intent.getSerializableExtra(EXTRA_DETAIL);
            if (dailyBean != null) {
                Glide.with(this).load(dailyBean.getPicUrl()).animate(R.anim.item_alpha_in).into
                        (bzdDetailImage);
                detailTitle.setText(dailyBean.getTitle());
                detailSource.setText(dailyBean.getPrice());
//                webView.loadUrl(dailyBean.getUrl());
                url = dailyBean.getUrl();
            }
        }
    }

    @OnClick(R.id.next_btn)
    void next() {
        Intent intent = new Intent(this, CommWebviewActivity.class);
        intent.putExtra(CommWebviewActivity.COMMON_WEB_URL, url);
        startActivity(intent);
    }

    public static void lanuch(Context context, BiezhiGoodsBean dailyBean) {
        Intent mIntent = new Intent(context, BiezhiDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, dailyBean);
        context.startActivity(mIntent);
    }
}
