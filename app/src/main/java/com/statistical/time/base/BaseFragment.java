/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.statistical.time.base;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.statistical.time.bean.EventCenter;
import com.statistical.time.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


//import com.squareup.leakcanary.RefWatcher;

/**
 * @version 1.0 2016/5/20
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T mPresenter;

   LoadingDialog mLoadingDialog;


    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(AppCompatActivity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (T) getPresenter();
        if (mPresenter!=null) mPresenter.onCreate();
//        LocaleLanguageUtil.getStance().changeLanguage(getActivity());
        if (isBindEventBusHere())
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }

    protected abstract boolean isBindEventBusHere();
    /**
     * when event comming
     */
    protected abstract void onEventComming(EventCenter eventCenter);

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = AmigoApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (isBindEventBusHere())
        EventBus.getDefault().unregister(this);
    }

    /**
     * 从本页面跳转到另外一个页面
     *
     * @param cls 需要跳转到的页面
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 带着数据，从本页面跳转到另外一个页面
     * @param cls 需要跳转到的页面
     * @param obj 传递给下个页面的数据
     */
    public void startActivity(Class<?> cls, Object obj) {
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivity(intent);
        //		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 带着数据，设置返回码，从本页面跳转到下个页面，重写onActivityResult可以获取从下个页面带回来的数据
     *
     * @param cls         需要跳转到的页面
     * @param obj         传递给下个页面的数据
     * @param requestCode 返回码
     */
    public void startActivityForResult(Class<?> cls, Object obj, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (obj != null) {
            intent.putExtra("data", (Serializable) obj);
        }
        startActivityForResult(intent, requestCode);
        //		overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public abstract BasePresenter getPresenter();

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter!=null) mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter!=null) mPresenter.onResume();
    }
    public void showLoadingDialog (String str,boolean cancancel){
        if (mLoadingDialog==null){
            mLoadingDialog = new LoadingDialog(getActivity());}
        mLoadingDialog.show();
        mLoadingDialog.setText(str);
        mLoadingDialog.setCancelable(cancancel);
    }

    public void dissmissLoadingDialog(){
        if (mLoadingDialog!=null) mLoadingDialog.dismiss();
    }

    public boolean  dialogIsShow(){
        if (mLoadingDialog!=null)
            return  mLoadingDialog.isShowing();
        return false;
    }



}
