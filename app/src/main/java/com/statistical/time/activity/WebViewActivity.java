package com.statistical.time.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.statistical.time.R;
import com.statistical.time.base.BaseActivity;
import com.statistical.time.base.BasePresenter;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.LogUtil;
import com.statistical.time.widget.ProgressWebView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @FileName：Camera1.5
 * @description：
 * @author：mac-likh
 * @date：2016/12/13 16:08
 */

public class WebViewActivity extends BaseActivity {
    InputMethodManager imm;
    public static final int PAGE_QUICK_GUIDE = 1; //快速手册
    public static final int PAGE_SPLASH      = 2; //闪屏
    private static final int FILECHOOSER_RESULTCODE  = 1;
    private static final int INPUT_FILE_REQUEST_CODE = 2;
    Unbinder unbinder;
    @BindView(R.id.tv_tittle)
    TextView        title;
    @BindView(R.id.webView)
    ProgressWebView webView;
    private String               url;
    private ValueCallback<Uri>   mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;
    private Serializable         obj;
    private boolean              showTitle;
    private boolean loadError        = false;
    private int     mCurrentPageFlag = -1; //页面标记

    public static void gotoWebViewActivity(Context context, String url, boolean showTitle) {
        if (context == null) {
            return;
        }
        gotoWebViewActivity(context, url, "", null, showTitle, -1);
    }

    //指定页面标记
    public static void gotoWebViewActivity(Context context, String url, boolean showTitle, int pageFlag) {
        if (context == null) {
            return;
        }
        gotoWebViewActivity(context, url, "", null, showTitle, pageFlag);
    }

    public static void gotoWebViewActivity(Context context, String url, String title, Serializable obj, boolean showTitle, int pageFlag) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("data", obj);
        intent.putExtra("showTitle", showTitle);
        intent.putExtra("currentPageFlag", pageFlag);
        context.startActivity(intent);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (mUploadMessage == null) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE) {
            if (mFilePathCallback == null) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mFilePathCallback.onReceiveValue(result == null ? null : new Uri[]{result});
            mFilePathCallback = null;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        unbinder = ButterKnife.bind(this);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mCurrentPageFlag = getIntent().getIntExtra("currentPageFlag", -1);
        url = getIntent().getStringExtra("url");
        obj = getIntent().getSerializableExtra("data");
        String title_str = getIntent().getStringExtra("title");
        showTitle = getIntent().getBooleanExtra("showTitle", true);

        if (!TextUtils.isEmpty(title_str)) {
            title.setText(title_str);
        }

        initWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(webView != null){
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(webView != null){
            webView.onPause();
        }
    }

    private void initWebView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.stopLoading();
//               view.loadUrl("about:blank");
                loadError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!loadError) {//当网页加载成功的时候判断是否加载成功
                    view.setEnabled(true);
                    view.setVisibility(View.VISIBLE);
                } else {
//                    view.loadUrl("about:blank");
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("url===","url==="+url);
                if (url.contains("bookdanceok.html")) {
                    finish();
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String titles) {
                super.onReceivedTitle(view, titles);
                if (!TextUtils.isEmpty(titles) && (titles.toLowerCase().contains("error") || titles.toLowerCase().contains("404") || titles.toLowerCase().contains("webpage not available"))) {
                    loadError = true;
                }
                if (showTitle) {
                    title.setText(titles);
                }
            }
        });


        webView.loadUrl(url);
    }

    @OnClick(R.id.iv_close)
    public void setBack() {

        if (imm.hideSoftInputFromWindow(webView.getWindowToken(), 0)){
        }else{
            if (webView != null && !loadError && webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止加载网页资源
        if(webView != null) {
            webView.clearCache(true);
            webView.clearHistory();
            webView.stopLoading();
            webView.destroy();
            webView.onPause();
        }
        unbinder.unbind();

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setBack();

    }

}
