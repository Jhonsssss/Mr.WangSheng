package com.statistical.time.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.statistical.time.R;
import com.statistical.time.application.ActivityManager;
import com.statistical.time.bean.EventCenter;
import com.statistical.time.tool.LanguageUtils;
import com.statistical.time.tool.LocaleLanguageUtil;
import com.statistical.time.tool.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;


public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity {

    protected T mPresenter;
    protected boolean mIsHasNavigationView;

    protected ProgressDialog mLoadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LanguageUtils.getCurrentLanguage(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        mPresenter =  (T)getPresenter();
        ActivityManager.getInstance().addActivity(this);
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
        if(isBindEventBusHere()){
            EventBus.getDefault().register(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//设置华为虚拟按钮
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }
    /**
     * startActivity
     */

    protected void showToast(String str){
        ToastUtil.showToast(this,str);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (mIsHasNavigationView) {
            overridePendingTransition(0, 0);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        ActivityManager.getInstance().removeActivity(this);
//        MyUtils.fixInputMethodManagerLeak(this);
        if(isBindEventBusHere()){
            EventBus.getDefault().unregister(this);
        }
    }
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }
    public void showLoadingDialog(boolean cancancel, DialogInterface.OnCancelListener cancelLis) {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog.setCancelable(cancancel);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            if(cancelLis!=null)
                mLoadingDialog.setOnCancelListener(cancelLis);
            mLoadingDialog.show();
        }
    }

    @Override
    public void startActivity(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LanguageUtils.getCurrentLanguage(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
//        LocaleLanguageUtil.getStance().changeLanguage(this);
        LanguageUtils.getCurrentLanguage(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Configuration.ORIENTATION_LANDSCAPE == newConfig.orientation) {
            LocaleLanguageUtil.getStance().changeLanguage(this);
        }
    }

    public void setBlackStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//实现状态栏图标和文字颜色为暗色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
    protected abstract boolean isBindEventBusHere();
    /**
     * when event comming
     */
    protected abstract void onEventComming(EventCenter eventCenter);


    public abstract BasePresenter getPresenter();
}
