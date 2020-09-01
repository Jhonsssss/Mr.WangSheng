package com.statistical.time.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * @FileName：Camera1.5
 * @description：
 * @author：mac-likh
 * @date：2016/12/13 19:01
 */

public class ProgressWebView extends WebView {
//    private ProgressBar progressbar;
    private android.webkit.WebChromeClient client;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
//        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10, 0, 0));
//        Drawable drawable = context.getResources().getDrawable(R.drawable.webview_progress_bar);
//        progressbar.setProgressDrawable(drawable);
//
//        addView(progressbar);

        initWebViewConfig();
    }

    private void initWebViewConfig() {

        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        this.setHorizontalScrollBarEnabled(false);
        super.setWebChromeClient(new MyWebChromeClient());

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= 19) {
            this.getSettings().setLoadsImagesAutomatically(true);
        } else {
            this.getSettings().setLoadsImagesAutomatically(false);
        }
        //webView.addJavascriptInterface(new Js(this), "app");
    }

    @Override
    public void setWebChromeClient(android.webkit.WebChromeClient client) {
        this.client = client;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//        lp.x = l;
//        lp.y = t;
//        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private class MyWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            if (newProgress == 100) {
//                progressbar.setVisibility(GONE);
//            } else {
//                if (progressbar.getVisibility() == GONE)
//                    progressbar.setVisibility(VISIBLE);
//                progressbar.setProgress(newProgress);
//            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (client != null) {
                client.onReceivedTitle(view, title);
            }
        }
    }
}
